#!/usr/bin/env bash

docker run --name producer \
-e kafkauri=http://127.0.0.1:9092 \
 -e topic=my_topic --net host --rm \
 miciav/producer:latest
