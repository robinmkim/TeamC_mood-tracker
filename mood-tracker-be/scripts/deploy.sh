#!/bin/bash

cd /home/ubuntu/deploy/build/libs

nohup java -jar jlpt-0.0.1-SNAPSHOT.jar > /home/ubuntu/jlpt/nohup.out 2>&1 &