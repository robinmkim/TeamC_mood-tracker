#!/bin/bash

cd /home/ubuntu/deploy/build/libs

nohup java -jar mood-tracker-be-0.0.1-SNAPSHOT.jar > /home/ubuntu/app/nohup.out 2>&1 &