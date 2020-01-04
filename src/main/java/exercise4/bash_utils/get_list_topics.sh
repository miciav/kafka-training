#!/usr/bin/env bash

docker run --net=host --rm confluentinc/cp-kafka \
kafka-topics --list --zookeeper localhost:2181