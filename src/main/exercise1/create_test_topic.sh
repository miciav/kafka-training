#!/usr/bin/env bash

docker run --net=host --rm confluentinc/cp-kafka \
kafka-topics --create --topic test \
--partitions 3 --replication-factor 3 \
--if-not-exists --zookeeper localhost:32181

