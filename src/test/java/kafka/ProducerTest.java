package kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mgd.AddressMgd;
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
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ProducerFencedException;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProducerTest {

    static Logger logger = Logger.getLogger(ProducerTest.class.getName());

    static String testProd;
    static String testCons;
    static KafkaProducer<UUID, String> producer;
    static KafkaConsumer<UUID, String> consumer1;
    static KafkaConsumer<UUID, String> consumer2;
    static List<KafkaConsumer<UUID, String>> consumerGroup = new ArrayList<>();

    static LocalDateTime t0 = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    static LocalDateTime t1 = t0.plusDays('1');

    static AddressMgd address1 = DataFakerMgd.getAddressMgd();
    static AddressMgd address2 = DataFakerMgd.getAddressMgd();
    static AddressMgd address3 = DataFakerMgd.getAddressMgd();

    static RentRepository repository = new RentRepository();
    static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void initProducer() throws ExecutionException, InterruptedException {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
//        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//        producerConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "4da972e7-ae0a-4e28-b133-1321007663a4");
        producer = new KafkaProducer<UUID, String>(producerConfig);

        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, Topics.CONSUMER_GROUP_NAME);//dynamiczny przydział
//consumerConfig.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "clientconsumer");//statyczny przydział
//        consumerConfig.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        consumer1 = new KafkaConsumer<UUID, String>(consumerConfig);
        consumer2 = new KafkaConsumer<UUID, String>(consumerConfig);
        consumer1.subscribe(List.of(Topics.CLIENT_TOPIC));
        consumer2.subscribe(List.of(Topics.CLIENT_TOPIC));
        consumerGroup.add(consumer1);
        consumerGroup.add(consumer2);
        objectMapper.registerModule(new JavaTimeModule());

    }

    @AfterAll
    public static void after() {
        List<RentMgd> mgdList = repository.getAll();

        for (RentMgd rentMgd :
                mgdList) {
            logger.warning("Rent: " + rentMgd.toString());
//            repository.remove(rentMgd);
        }

//        logger.warning(repository.getAll().toString());
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
            logger.warning(ee.getCause().getMessage());
            Assertions.assertEquals(ee.getCause().getClass(), TopicExistsException.class);
        }
    }

    private void onCompletion(RecordMetadata data, Exception exception) {
        if (exception == null) {
            logger.warning(String.valueOf(data.offset()));
        } else {
            logger.warning(exception.getMessage());
        }
    }


    @Test
    @Order(2)
    public void sendMessages() throws ExecutionException, InterruptedException, JsonProcessingException {
//        producer.initTransactions();
        try {
            for (int i = 0; i < 5; i++) {
                RentMgd rentMgd = DataFakerMgd.getRentMgd(t0, t1);
                logger.warning(rentMgd.toString());
                ProducerRecord<UUID, String> rentRecord = new ProducerRecord<>(Topics.CLIENT_TOPIC,
                        rentMgd.getEntityId().getUuid(), objectMapper.writeValueAsString(rentMgd));
                producer.send(rentRecord);
            }
//            producer.commitTransaction();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ProducerFencedException e) {
//            producer.close();
        } catch (KafkaException ke) {
//            producer.abortTransaction();
        }
//        RecordMetadata recordMetadata = sent.get();
//        testProd = recordMetadata.toString();
    }


    @Test
    @Order(3)
    public void consumeTopicsByGroup() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        logger.warning("consumer group: " + consumerGroup);
        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
            logger.warning(consumer.toString());
            executorService.execute(() -> consume(consumer));
        }
        Thread.sleep(10000);
        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
            consumer.wakeup();
        }
        executorService.shutdown();
        deleteConsumerGroup();
    }

    private void consume(KafkaConsumer<UUID, String> consumer) {
//        try {
        consumer.poll(Duration.ZERO);
        Set<TopicPartition> consumerAssignment = consumer.assignment();
        logger.warning("here consumer");
        consumer.seekToBeginning(consumerAssignment);
        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat("Konsument {5},Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");

        try {
            while (true) {
//                logger.warning(consumer.toString());
                ConsumerRecords<UUID, String> records;
                records = consumer.poll(timeout);
//                logger.warning(consumer.toString() + "    " + String.valueOf(records.count()));


                for (ConsumerRecord<UUID, String> record : records) {
                    String value = record.value();
                    logger.warning("RECORD VALUE" + value);
                    saveToDB(value);
                    String result = formatter.format(new Object[]{
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.key(),
                            record.value(),
                            consumer.groupMetadata().memberId()

                    });
                    logger.warning(result);
                }
            }
        } catch (WakeupException we) {
            logger.warning("Job Finished");
        }
    }

    private void saveToDB(String value) {
        try (RentMgd rent = objectMapper.readValue(value, RentMgd.class)) {
            repository.add(rent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
                logger.warning("Consumer group description");
                logger.warning(consumerGroupDescription.toString());
            }
            admin.deleteConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));
        }
    }
}