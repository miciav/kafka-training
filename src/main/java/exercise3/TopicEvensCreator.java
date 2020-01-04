package exercise3;

import com.sun.tools.javac.util.List;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class TopicEvensCreator {

    public static void main(String[] args) {

        Properties propertiesProducer = new Properties();
        propertiesProducer.setProperty(
                BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        AdminClient adminClient = KafkaAdminClient.create(propertiesProducer);
        adminClient.createTopics(List.of(new NewTopic("evens", 2, (short) 1)));


    }
}
