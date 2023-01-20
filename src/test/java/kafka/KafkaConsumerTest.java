package kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mgd.RentMgd;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.junit.jupiter.api.*;
import repository.impl.RentRepository;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KafkaConsumerTest {

    static Logger logger = Logger.getLogger(KafkaProducerTest.class.getName());

    static List<KafkaConsumer<UUID, String>> consumerGroup = new ArrayList<>();

    static RentRepository repository = new RentRepository();

    static ObjectMapper objectMapper = new ObjectMapper();

    static Duration timeout = Duration.of(1000, ChronoUnit.MILLIS);
    static MessageFormat formatter = new MessageFormat("Konsument {5},Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");

    static Properties consumerConfig = new Properties();

    @BeforeAll
    public static void init() {

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

    @AfterAll
    public static void destruct() throws ExecutionException, InterruptedException {
        deleteConsumerGroup();
    }

    @Test
    @Order(1)
    public void consumeTest() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        consumerGroup.add(new KafkaConsumer<UUID, String>(consumerConfig));
        consumerGroup.add(new KafkaConsumer<UUID, String>(consumerConfig));

//        Thread.sleep(5000);
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

        executorService.shutdown();
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

                        result = formatter.format(new Object[]{record.topic(), record.partition(), record.offset(), record.key(), record.value(), kafkaConsumer.groupMetadata().memberId()

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
    @Order(2)
    public void printDatabase() {
        List<RentMgd> mgdList = repository.getAll();

        if (mgdList.isEmpty()) {
            logger.warning("MONGO EMPTY");
        }
        for (RentMgd rentMgd : mgdList) {
            logger.info("\u001B[34m" + "MONGO Database: " + rentMgd + "\u001B[0m");
        }
    }

    private List<ConsumerGroupDescription> consumerGroupDescriptions() {
        List<ConsumerGroupDescription> groupDescriptions = new ArrayList<ConsumerGroupDescription>();

        Properties adminConfig = new Properties();
        adminConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");

        try (Admin admin = Admin.create(adminConfig)) {
            DescribeConsumerGroupsResult describeConsumerGroupsResult = admin.describeConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));

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

    private static void deleteConsumerGroup() throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        try (Admin admin = Admin.create(properties)) {
            DescribeConsumerGroupsResult describeConsumerGroupsResult = admin.describeConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));
            Map<String, KafkaFuture<ConsumerGroupDescription>> describedGroups = describeConsumerGroupsResult.describedGroups();
            for (Future<ConsumerGroupDescription> group : describedGroups.values()) {
                ConsumerGroupDescription consumerGroupDescription = group.get();
                logger.info("\u001B[34m" + "Consumer group description: " + "\u001B[0m");
                logger.info("\u001B[34m" + consumerGroupDescription + "\u001B[0m");
            }
        }
        try(Admin admin = Admin.create(properties)) {
        admin.deleteConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));
        }

    }
}
