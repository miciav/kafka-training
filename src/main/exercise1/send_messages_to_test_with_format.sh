#!/usr/bin/env bash


docker run  --net=host --rm \
confluentinc/cp-kafka \
bash -c "for i in \$(seq 42); do echo \${i},\$RANDOM; done | kafka-console-producer --broker-list localhost:29092 --topic test --property parse.key=true --property key.separator=, "
