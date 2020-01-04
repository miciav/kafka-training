#!/usr/bin/env bash

for i in 22181 32181 42181; do
   docker run --net=host --rm confluentinc/cp-zookeeper:latest bash -c "echo stat | nc localhost $i | grep Mode"
done