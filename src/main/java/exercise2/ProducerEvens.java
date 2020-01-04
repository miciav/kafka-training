package exercise2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ProducerEvens {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.setProperty(
                BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        properties.setProperty(
                KEY_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());
        properties.setProperty(
                VALUE_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());

        KafkaProducer<Integer, Integer> producer = new KafkaProducer<>(properties);
        String topic = "evens";
        int i = 0;

        try {
            while (true) {
                i += 1;
                producer.send(new ProducerRecord<>(topic, i, i*2));
                System.out.println("Key = "+ i+" - Value = "+i*2);
                Thread.sleep(500);
            }
        } finally {
            producer.close();
        }
    }
}
