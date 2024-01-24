#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app/"
JAR_FILE="$PROJECT_ROOT/build/libs/mood-tracker-be-0.0.1-SNAPSHOT.jar"

DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# 현재 구동 중인 애플리케이션 pid 확인
CURRENT_PID=$(pgrep -f $JAR_FILE)

pkill -f "$CURRENT_PID"

# 프로세스가 켜져 있으면 종료
if [ $? -eq 0 ]; then
  echo "$TIME_NOW > 실행중인 애플리케이션을 종료했습니다" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행중인 애플리케이션이 없습니다" >> $DEPLOY_LOG
fi