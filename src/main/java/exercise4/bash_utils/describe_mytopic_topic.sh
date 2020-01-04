#!/usr/bin/env bash

docker run --net=kafka-net --rm confluentinc/cp-kafka kafka-topics --describe --topic my_topic --zookeeper zookeeper:2181
