package exercise3;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

public class DeleteAllTopics {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties propertiesProducer = new Properties();
        propertiesProducer.setProperty(
                BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");

        AdminClient adminClient = KafkaAdminClient.create(propertiesProducer);
        Set<String> topicsNames = adminClient.listTopics().names().get();
        topicsNames.forEach(System.out::println);
        adminClient.deleteTopics(topicsNames);

        if (adminClient.listTopics().names().get().isEmpty()) System.out.println("All topics have been removed");
    }
}
