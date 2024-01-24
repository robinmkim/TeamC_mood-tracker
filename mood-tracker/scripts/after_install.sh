#!/bin/bash

# Nginx 설정 업데이트 (예: default 파일 수정)
echo "Updating Nginx configuration..."
cat <<EOL > /etc/nginx/sites-available/default
server {
    listen 80;
    server_name your_domain.com; # 도메인 이름으로 변경

    location / {
        root /var/www/html/dist; # Vue.js 애플리케이션의 dist 디렉토리 경로로 변경
        try_files \$uri \$uri/ /index.html;
    }

    # 다른 설정들...

    # HTTPS 설정은 필요한 경우 추가
}
EOL

# Nginx 재시작
echo "Restarting Nginx..."
service nginx restart

# 다른 작업들...
