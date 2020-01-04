#!/usr/bin/env bash

docker run \
  --net=host \
  --rm \
  confluentinc/cp-kafka\
  kafka-console-consumer --bootstrap-server localhost:29092 --topic test --from-beginning --partition 0
