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

        try (KafkaDispatcher dispatcher = new KafkaDispatcher()) {
            for (int i = 0; i < 10; i++) {
                String key = UUID.randomUUID().toString();
                String value = key + ",6852741,1234";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                String email = "Thank you for your order!";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, value);
            }
        }

    }


}
