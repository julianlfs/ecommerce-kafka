import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.record.Record;
import org.apache.kafka.common.record.Records;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class FraudDetectorService {

    public static void main(String[] args) {

        FraudDetectorService fraudDetectorService = new FraudDetectorService();
        KafkaService kafkaService = new KafkaService(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER", fraudDetectorService::parse);
        kafkaService.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("-------------------------------------------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Order processed");
    }
}
