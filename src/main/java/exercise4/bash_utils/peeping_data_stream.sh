#!/bin/sh

# kafkacat -C -b localhost:9092 -t my_topic
#docker exec -it kafka /opt/kafka/bin/./kafka-console-consumer.sh --zookeeper zookeeper --topic my_topic

#docker exec -it kafka /opt/kafka/bin/./kafka-simple-consumer-shell.sh --broker-list localhost:9092 --topic my_topic --partition 0


docker run --net=host --rm confluentinc/cp-kafka \
kafka-console-consumer --bootstrap-server localhost:9092 --topic my_topic --group my_topic_group