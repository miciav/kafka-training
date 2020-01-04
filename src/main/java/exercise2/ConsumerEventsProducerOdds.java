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

public class ConsumerEventsProducerOdds {

    public static void main(String[] args) throws InterruptedException {

        Properties propertiesConsumer = new Properties();

        propertiesConsumer.setProperty(
                BOOTSTRAP_SERVERS_CONFIG,
                "127.0.0.1:9092");

        propertiesConsumer.setProperty(
                KEY_DESERIALIZER_CLASS_CONFIG,
                IntegerDeserializer.class.getName());

        propertiesConsumer.setProperty(
                VALUE_DESERIALIZER_CLASS_CONFIG,
                IntegerDeserializer.class.getName());

        propertiesConsumer.setProperty(
                GROUP_ID_CONFIG,
                "even-consumer-odds-producer");


        KafkaConsumer<Integer, Integer> consumer = new KafkaConsumer<>(propertiesConsumer);


        consumer.subscribe(Arrays.asList("evens"));
        consumer.seekToBeginning(consumer.assignment());

        Properties propertiesProducer = new Properties();
        propertiesProducer.setProperty(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "127.0.0.1:9092");
        propertiesProducer.setProperty(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());
        propertiesProducer.setProperty(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());

        // create a new KafkaProducer object (key=Integer, value=Integer)
        KafkaProducer<Integer, Integer> producer = new KafkaProducer<>(propertiesProducer);

        //infinite read-transform-send loop
        while (true) {

            // read record using poll method of the consumer
            ConsumerRecords<Integer, Integer> records = consumer.poll(Duration.ofMillis(100));

            //for each record read send a new record with value = read_value -1 on the odds topic
            records.forEach(
                    r -> {
                        System.out.println("timestamp = "+r.timestamp());
                        System.out.println("offset = " +r.offset());
                        System.out.println("partition = "+r.partition());
                        System.out.println("key = "+r.key());
                        System.out.println("value = "+r.value());

                        ProducerRecord<Integer, Integer> record;
                        record = new ProducerRecord<>("odds", r.key(), r.value() - 1);

                        //send record on the "odds" topic using hte send method of the producer
                        producer.send(record);

                    });

            // sleep for 500 milliseconds
            Thread.sleep(500);
        }
    }
}
