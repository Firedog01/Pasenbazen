package kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mgd.DataFakerMgd;
import mgd.RentMgd;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KafkaProducerTest {

    static Logger logger = Logger.getLogger(KafkaProducerTest.class.getName());

    static KafkaProducer<UUID, String> producer;

    static LocalDateTime t0 = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    static LocalDateTime t1 = t0.plusDays('1');

    static RentRepository repository = new RentRepository();

    static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void init() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
//        producerConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "4da972e7-ae0a-4e28-b133-1321007663a4");
        producer = new KafkaProducer<UUID, String>(producerConfig);

        objectMapper.registerModule(new JavaTimeModule());

        repository.getDb().drop();

        logger.info("\u001B[34m" + "testBeforeAll" + "\u001B[0m");
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
}