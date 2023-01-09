package kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mgd.AddressMgd;
import mgd.ClientMgd;
import mgd.DataFakerMgd;
import mgd.EQ.EquipmentMgd;
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
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Disabled //Not working for now, error in producer method
public class ProducerTest {

    static KafkaProducer<UUID, String> producer;
    static KafkaConsumer consumer;
    static List<KafkaConsumer<UUID, String>> consumerGroup = new ArrayList<>();

    static LocalDateTime t0 = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    static LocalDateTime t1 = t0.plusDays('1');

    static AddressMgd address1 = DataFakerMgd.getAddressMgd();
    static AddressMgd address2 = DataFakerMgd.getAddressMgd();
    static AddressMgd address3 = DataFakerMgd.getAddressMgd();

    static ClientMgd client1 = DataFakerMgd.getClientMgd(address1);
    static ClientMgd client2 = DataFakerMgd.getClientMgd(address2);
    static ClientMgd client3 = DataFakerMgd.getClientMgd(address3);

    static EquipmentMgd camera = DataFakerMgd.getCameraMgd();
    static EquipmentMgd lens = DataFakerMgd.getLensMgd();
    static EquipmentMgd trivet = DataFakerMgd.getTrivetMgd();

    @BeforeAll
    public static void initProducer() throws ExecutionException, InterruptedException {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9191,kafka2:9291,kafka3:9391");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "0fdf73dd-bd48-48f1-bb1b-625ba29f6c04");
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producer = new KafkaProducer<UUID, String>(producerConfig);

        Properties consumerConfig = new Properties();
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, Topics.CONSUMER_GROUP_NAME);//dynamiczny przydział
//consumerConfig.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "clientconsumer");//statyczny przydział
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9191,kafka2:9291,kafka3:9391");
        consumer = new KafkaConsumer(consumerConfig);
        consumer.subscribe(List.of(Topics.CLIENT_TOPIC));
    }



    @Test
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
            System.out.println(ee.getCause().getMessage());
            Assertions.assertEquals(ee.getCause().getClass(), TopicExistsException.class);
        }
    }

    private void onCompletion(RecordMetadata data, Exception exception) {
        if (exception == null) {
            System.out.println(data.offset());
        } else {
            System.out.println(exception.getMessage());
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
                System.out.println(consumerGroupDescription);
            }
            admin.deleteConsumerGroups(List.of(Topics.CONSUMER_GROUP_NAME));
        }
    }

    @Test
    public void sendMessages() throws ExecutionException, InterruptedException, JsonProcessingException {
//        initProducer();
        RentMgd rentMgd = DataFakerMgd.getRentMgd(t0, t1);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        ProducerRecord<UUID, String> rentRecord = new ProducerRecord<>(Topics.CLIENT_TOPIC,
                rentMgd.getEntityId().getUuid(), "objectMapper.writeValueAsString(rentMgd)");
        System.out.println(rentRecord);
        Future<RecordMetadata> sent = producer.send(rentRecord); //FIXME Error
        // java.util.concurrent.ExecutionException: org.apache.kafka.common.errors.
        // UnsupportedVersionException: The broker does not support METADATA
//        producer.send(rentRecord, this::onCompletion);
        RecordMetadata recordMetadata = sent.get();
//        System.out.println(objectMapper.writeValueAsString(rentMgd));
        System.out.println(recordMetadata.toString());

    }

    private void receiveClients() {
        int messagesReceived = 0;
        Map<Integer, Long> offsets = new HashMap<>();
        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat("Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");
        while (messagesReceived < 100) { //while true?
            ConsumerRecords<UUID, String> records = consumer.poll(timeout);
            for (ConsumerRecord<UUID, String> record : records) {
                String result = formatter.format(new Object[]{record.topic(), record.partition(),
                        record.offset(), record.key(), record.value()});
                System.out.println(result);
                offsets.put(record.partition(), record.offset());
                messagesReceived++;
            }
        }
        System.out.println(offsets);
        consumer.commitAsync();
    } //FIXME DISABLE AUTOCOMMIT? BUT WHERE


    public void receiveFromBeginning() { //KOD NIE PRODUKCYJNY, CZYLI NIE NADAJE SIĘ?
        consumer.poll(0);
        Set<TopicPartition> consumerAssignment = consumer.assignment();
        System.out.println(consumerAssignment);
        consumer.seekToBeginning(consumerAssignment);
        Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
        MessageFormat formatter = new MessageFormat("Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");
        Set<TopicPartition> unfinishedPartitions = new HashSet<>(consumerAssignment);
        while (!unfinishedPartitions.isEmpty()) {
            ConsumerRecords<UUID, String> records = consumer.poll(timeout);
            for (ConsumerRecord<UUID, String> record : records) {
                String result = formatter.format(new Object[]{record.topic(), record.partition(), record.offset(), record.key(),
                        record.value()});
                System.out.println(result);
                for (TopicPartition partition : consumerAssignment) {
                    long position = consumer.position(partition, timeout);
                    if (partition.partition() == record.partition() && record.offset() == position - 1) {
                        unfinishedPartitions.remove(partition);
                    }
                }
            }
        }
    }



    public void consumeTopicsByGroup() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
            executorService.execute(() -> consume(consumer));
        }
        Thread.sleep(10000);
        for (KafkaConsumer<UUID, String> consumer : consumerGroup) {
            consumer.wakeup();
        }
        executorService.shutdown();
    }

    private void consume(KafkaConsumer<UUID, String> consumer) {
        try {
            consumer.poll(0);
            Set<TopicPartition> consumerAssignment = consumer.assignment();
            System.out.println(consumer.groupMetadata().memberId() + " " + consumerAssignment);
            consumer.seekToBeginning(consumerAssignment);
            Duration timeout = Duration.of(100, ChronoUnit.MILLIS);
            MessageFormat formatter = new MessageFormat("Konsument {5},Temat {0}, partycja {1}, offset {2, number, integer}, klucz {3}, wartość {4}");
            while (true) {
                ConsumerRecords<UUID, String> records = consumer.poll(timeout);
                for (ConsumerRecord<UUID, String> record : records) {
                    String result = formatter.format(new Object[]{
                            record.topic(),
                            record.partition(),
                            record.offset(),
                            record.key(),
                            record.value(),
                            consumer.groupMetadata().memberId()
                    });
                    System.out.println(result);
                }
            }
        } catch (WakeupException we) {
            System.out.println("Job Finished");
        }
    }
}