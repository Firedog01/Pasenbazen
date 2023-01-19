package kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mgd.DataFakerMgd;
import mgd.RentMgd;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.junit.jupiter.api.*;
import repository.impl.RentRepository;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KafkaTest {

    static Logger logger = Logger.getLogger(KafkaTest.class.getName());

    static KafkaProducer<UUID, String> producer;

    static List<KafkaConsumer<UUID, String>> consumerGroup = new ArrayList<>();

    static LocalDateTime t0 = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    static LocalDateTime t1 = t0.plusDays('1');

    static RentRepository repository = new RentRepository();

    static ObjectMapper objectMapper = new ObjectMapper();

    static Duration timeout = Duration.of(200, ChronoUnit.MILLIS);
    static MessageFormat formatter = new MessageFormat("Konsument {5},Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");

    static Properties consumerConfig = new Properties();

    @BeforeAll
    public static void initProducer() throws ExecutionException, InterruptedException {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//        producerConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "4da972e7-ae0a-4e28-b133-1321007663a4");
        producer = new KafkaProducer<UUID, String>(producerConfig);

        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, Topics.CONSUMER_GROUP_NAME);//dynamiczny przydział
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
//        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        consumerConfig.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");


        objectMapper.registerModule(new JavaTimeModule());

        repository.getDb().drop();

        logger.info("\u001B[34m" + "testBeforeAll" + "\u001B[0m");
    }


    private List<ConsumerGroupDescription> consumerGroupDescriptions() {
        List<ConsumerGroupDescription> groupDescriptions = new ArrayList<ConsumerGroupDescription>();

        Properties adminConfig = new Properties();
        adminConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");

        try (Admin admin = Admin.create(adminConfig)) {
            DescribeConsumerGroupsResult describeConsumerGroupsResult =
                    admin.describeConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));

            Map<String, KafkaFuture<ConsumerGroupDescription>> describedGroups = describeConsumerGroupsResult.describedGroups();

            for (Future<ConsumerGroupDescription> group : describedGroups.values()) {
                ConsumerGroupDescription consumerGroupDescription;
                try {
                    consumerGroupDescription = group.get();
                    groupDescriptions.add(consumerGroupDescription);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return groupDescriptions;

    }

    private void deleteConsumerGroup() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        try (Admin admin = Admin.create(properties)) {
            DescribeConsumerGroupsResult describeConsumerGroupsResult =
                    admin.describeConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));
            Map<String, KafkaFuture<ConsumerGroupDescription>> describedGroups = describeConsumerGroupsResult.describedGroups();
            for (Future<ConsumerGroupDescription> group : describedGroups.values()) {
                ConsumerGroupDescription consumerGroupDescription = group.get();
                logger.info("\u001B[34m" + "Consumer group description: " + "\u001B[0m");
                logger.info("\u001B[34m" + consumerGroupDescription + "\u001B[0m");
            }

            admin.deleteConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));
        }
    }

    @Test
    @Order(1)
    public void createTopic() throws InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        int partitionsNumber = 3;
        short replicationFactor = 3;
        try (Admin admin = Admin.create(properties)) {
            NewTopic newTopic = new NewTopic(Topics.CLIENT_TOPIC, partitionsNumber, replicationFactor);
            CreateTopicsOptions options = new CreateTopicsOptions()
                    .timeoutMs(1000)
                    .validateOnly(false)
                    .retryOnQuotaViolation(true);
            CreateTopicsResult result = admin.createTopics(List.of(newTopic), options);
            KafkaFuture<Void> futureResult = result.values().get(Topics.CLIENT_TOPIC);
            futureResult.get();
        } catch (ExecutionException ee) {
            logger.warning("\u001B[31m" + ee.getCause().getMessage() + "\u001B[0m");
            Assertions.assertEquals(ee.getCause().getClass(), TopicExistsException.class);
        }
    }

    @Test
    @Order(2)
    public void sendMessages() {
        try {
            for (int i = 0; i < 5; i++) {
                RentMgd rentMgd = DataFakerMgd.getRentMgd(t0, t1);
                logger.info("\u001B[34m" + rentMgd + "\u001B[0m");

                ProducerRecord<UUID, String> rentRecord = new ProducerRecord<>(Topics.CLIENT_TOPIC,
                        rentMgd.getEntityId().getUuid(), objectMapper.writeValueAsString(rentMgd));

                Future<RecordMetadata> sent = producer.send(rentRecord);
                RecordMetadata recordMetadata = sent.get();
                logger.info("\u001B[34m" + recordMetadata + "\u001B[0m");
            }
        } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
            logger.warning("\u001B[31m" + e.getCause().getMessage() + "\u001B[0m");
        }
    }

    @Test
    @Order(3)
    public void consumeTest() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        consumerGroup.add(new KafkaConsumer<UUID, String>(consumerConfig));
        consumerGroup.add(new KafkaConsumer<UUID, String>(consumerConfig));

        Thread.sleep(5000);
        int i = 1;
        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
            int finalI = i;
            executorService.execute(() -> consumerTest(String.valueOf(finalI), consumer));
            i++;
        }
        Thread.sleep(5000);
        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
            consumer.wakeup();
        }

        executorService.awaitTermination(10, TimeUnit.SECONDS);
        executorService.shutdown();
        deleteConsumerGroup();
    }

    private void consumerTest(String number, KafkaConsumer<UUID, String> kafkaConsumer) {

        kafkaConsumer.subscribe(Collections.singleton(Topics.CLIENT_TOPIC));

        for (ConsumerGroupDescription description : consumerGroupDescriptions()) {
            logger.info("\u001B[34m" + description + "\u001B[0m");

            for (MemberDescription member : description.members()) {
                logger.info("\u001B[34m" + member + "\u001B[0m");
            }


            logger.info("\u001B[34m" + number + "\u001B[0m");
            try {
                String result = null;
                while (true) {

                    ConsumerRecords<UUID, String> records = kafkaConsumer.poll(timeout);


                    for (ConsumerRecord<UUID, String> record : records) {
                        String value = record.value();
                        logger.info("\u001B[34m" + "RECORD VALUE" + value + "\u001B[0m");

                        result = formatter.format(new Object[]{
                                record.topic(),
                                record.partition(),
                                record.offset(),
                                record.key(),
                                record.value(),
                                kafkaConsumer.groupMetadata().memberId()

                        });

                        try (RentMgd rent = objectMapper.readValue(value, RentMgd.class)) {
                            repository.add(rent);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    kafkaConsumer.commitSync();

                    logger.info("\u001B[34m" + result + "\u001B[0m");

                    kafkaConsumer.wakeup();
                }
            } catch (WakeupException e) {
                logger.info("\u001B[34m" + "job finished" + "\u001B[0m");

            }
        }
    }

    @Test
    @Order(4)
    public void printDatabase() {
        List<RentMgd> mgdList = repository.getAll();

        if (mgdList.isEmpty()) {
            logger.warning("MONGO EMPTY");
        }
        for (RentMgd rentMgd :
                mgdList) {
            logger.info("\u001B[34m" + "MONGO Database: " + rentMgd + "\u001B[0m");
        }
    }


//    @Test
//    @Order(3)
//    public void consumeTopicsByGroup() throws InterruptedException, ExecutionException {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        logger.info("\u001B[34m" + "consumer group: " + consumerGroup + "\u001B[0m");
//
//
//        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
//            executorService.execute(() -> consume(consumer));
//        }
//
//
//        Thread.sleep(10000);
//        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
//            consumer.wakeup();
//        }
//        executorService.awaitTermination(10, TimeUnit.SECONDS);
//        executorService.shutdown();
//        deleteConsumerGroup();
//    }


//    private void consume(KafkaConsumer<UUID, String> consumer) {
//        consumer.poll(Duration.ZERO);
//        Set<TopicPartition> consumerAssignment = consumer.assignment();
//        logger.info("\u001B[34m" + "here consumer" + "\u001B[0m");
//        consumer.seekToBeginning(consumerAssignment);
//        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
//        MessageFormat formatter = new MessageFormat("Konsument {5},Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");
//        List<String> values = new ArrayList<>();
//        try {
//            while (true) {
//                ConsumerRecords<UUID, String> records;
//                records = consumer.poll(timeout);
//
//                for (ConsumerRecord<UUID, String> record : records) {
//                    String value = record.value();
//                    logger.info("\u001B[34m" + "RECORD VALUE" + value + "\u001B[0m");
//                    values.add(value);
//                    String result = formatter.format(new Object[]{
//                            record.topic(),
//                            record.partition(),
//                            record.offset(),
//                            record.key(),
//                            record.value(),
//                            consumer.groupMetadata().memberId()
//
//                    });
//                    logger.info("\u001B[34m" + result + "\u001B[0m");
//                }
//            }
//        } catch (WakeupException we) {
//            logger.info("\u001B[34m" + "job finished" + "\u001B[0m");
//
//            RentRepository repository = new RentRepository();
//            for (String value :
//                    values) {
//                saveToDB(repository, value);
//            }
//        }
//    }

}