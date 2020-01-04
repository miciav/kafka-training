#!/usr/bin/env bash

docker run --net=host --rm confluentinc/cp-kafka \
kafka-consumer-groups --bootstrap-server localhost:9092 --list