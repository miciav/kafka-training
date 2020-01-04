package exercise3;

import com.sun.tools.javac.util.List;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class ProducerEvens {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Properties propertiesProducer = new Properties();
        propertiesProducer.setProperty(
                BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        propertiesProducer.setProperty(
                KEY_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());
        propertiesProducer.setProperty(
                VALUE_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class.getName());

        AdminClient adminClient = KafkaAdminClient.create(propertiesProducer);
        CreateTopicsResult res = adminClient.createTopics(List.of(new NewTopic("evens", 2, (short) 1)));

        KafkaProducer<Integer, Integer> producer = new KafkaProducer<>(propertiesProducer);
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
