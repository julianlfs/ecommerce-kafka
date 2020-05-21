import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import sun.security.timestamp.TSRequest;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String TOPIC_NAME = "ECOMMERCE_NEW_ORDER";

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());
        String key = UUID.randomUUID().toString();
        String value = key + ",6852741,1234";
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, key, value);
       Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
        };

        producer.send(producerRecord, callback).get();

        String email = "Thank you for your order!";
        ProducerRecord<String, String> emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key, email);

        producer.send(emailRecord, callback).get();
    }

    private static Properties properties() {

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
