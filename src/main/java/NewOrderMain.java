import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import sun.security.timestamp.TSRequest;

import java.util.Map;
import java.util.Properties;

public class NewOrderMain {

    public static void main(String[] args) {

        String TOPIC_NAME = "ECOMMERCE_NEW_ORDER";

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());
        String value = "123,6852,755412";
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, value, value);
        producer.send(producerRecord);
    }

    private static Properties properties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
