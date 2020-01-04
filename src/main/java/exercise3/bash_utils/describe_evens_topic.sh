#!/usr/bin/env bash

docker run --net=kafka-net --rm confluentinc/cp-kafka kafka-topics --describe --topic evens --zookeeper zookeeper:2181
