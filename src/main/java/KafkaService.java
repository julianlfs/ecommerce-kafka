import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService implements Closeable {

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final ConsumerFunction parse;

    public KafkaService(String groupId, String topic, ConsumerFunction parse) {
        this(parse, groupId);
        kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    public KafkaService(String groupId, Pattern topic, ConsumerFunction parse) {
        this(parse, groupId);
        kafkaConsumer.subscribe(topic);
    }

    private KafkaService(ConsumerFunction parse, String groupId) {
        this.parse = parse;
        kafkaConsumer = new KafkaConsumer<>(properties(groupId));
    }

    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty())
                System.out.println("Encontrei " + records.count() + " registros");

            for (ConsumerRecord<String, String> record : records) {
                parse.consume(record);
            }
        }
    }

    private static Properties properties(String groupId) {

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        return properties;
    }

    @Override
    public void close() {
        kafkaConsumer.close();
    }
}
