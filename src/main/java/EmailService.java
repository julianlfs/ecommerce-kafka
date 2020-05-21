import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {

    public static void main(String[] args) {

        EmailService emailService = new EmailService();
        KafkaService kafkaService = new KafkaService(EmailService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL", emailService::parse);
        kafkaService.run();
    }

      private void parse(ConsumerRecord<String, String> record) {
            System.out.println("-------------------------------------------------");
            System.out.println("Send email");
            System.out.println(record.key());
            System.out.println(record.value());
            System.out.println(record.partition());
            System.out.println(record.offset());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Email sent");
    }


}
