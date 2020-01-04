#!/usr/bin/env bash

docker run --net=host --rm confluentinc/cp-kafka kafka-topics --describe --topic test --zookeeper localhost:32181
