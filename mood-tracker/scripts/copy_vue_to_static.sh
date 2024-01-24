#!/bin/bash
# Vue.js 빌드 결과물을 Spring Boot의 static 디렉토리로 복사
cp -R /tmp/vue-dist/* /var/app/mood-tracker-be/src/main/resources/static/
