#!/usr/bin/env bash

#!/bin/bash

TOPICS=$(docker run --net=host --rm confluentinc/cp-kafka kafka-topics --zookeeper localhost:2181 --list )

for T in ${TOPICS}
do
  if [[ "$T" != "__consumer_offsets" ]]; then
    docker run --net=host --rm confluentinc/cp-kafka kafka-topics --zookeeper localhost:2181 --delete --topic ${T}
  fi
done