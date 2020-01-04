package exercise2;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class ConsumerOdds {

    public static void main(String[] args) throws InterruptedException {

        Properties properties = new Properties();
        properties.setProperty(
                BOOTSTRAP_SERVERS_CONFIG,
                "127.0.0.1:9092");
        properties.setProperty(
                KEY_DESERIALIZER_CLASS_CONFIG,
                IntegerDeserializer.class.getName());
        properties.setProperty(
                VALUE_DESERIALIZER_CLASS_CONFIG,
                IntegerDeserializer.class.getName());
        properties.setProperty(
                GROUP_ID_CONFIG,
                "even-consumer-odds-producer");


        KafkaConsumer<Integer, Integer> consumer =
                new KafkaConsumer<Integer, Integer>(properties);

        consumer.subscribe(Arrays.asList("odds"));

        consumer.poll(Duration.ofMillis(1500));

        consumer.seekToBeginning(consumer.assignment());


        while (true) {

            ConsumerRecords<Integer, Integer> records = consumer.poll(Duration.ofMillis(100));

            records.forEach(
                    r -> {

                        System.out.println("timestamp = "+r.timestamp());
                        System.out.println("offset = " +r.offset());
                        System.out.println("partition = "+r.partition());
                        System.out.println("key = "+r.key());
                        System.out.println("value = "+r.value());

                    });

            Thread.sleep(500);
        }
    }
}
