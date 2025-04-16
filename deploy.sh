#!/bin/bash

# 기본 설정
APP_NAME=scenario-backend
BLUE_PORT=8081
GREEN_PORT=8082
EC2_HOST=13.125.236.97  # 또는 ec2 인스턴스 ip
JAR_PATH=./build/libs/*.jar

# 현재 실행 중인 포트 확인
CURRENT_PORT=$(docker ps --filter "name=$APP_NAME-blue" --format "{{.Ports}}" | grep -oP '\d+(?=->8080)' || echo "")

if [ "$CURRENT_PORT" == "$BLUE_PORT" ]; then
  TARGET=green
  TARGET_PORT=$GREEN_PORT
else
  TARGET=blue
  TARGET_PORT=$BLUE_PORT
fi

echo "🔄 배포할 대상: $TARGET ($TARGET_PORT)"

# 새 컨테이너 빌드 및 실행
echo "📦 새 이미지 빌드 중..."
docker build -t $APP_NAME:$TARGET .

echo "🧼 기존 $TARGET 컨테이너 제거 중..."
docker rm -f $APP_NAME-$TARGET 2>/dev/null

echo "🚀 새 컨테이너 실행 중..."
docker run -d --name $APP_NAME-$TARGET -p $TARGET_PORT:8080 $APP_NAME:$TARGET

# Health check
echo "💊 헬스 체크 중..."
for i in {1..10}; do
  sleep 3
  RESPONSE=$(curl -s http://$EC2_HOST:$TARGET_PORT/actuator/health | jq -r '.status')
  if [ "$RESPONSE" == "UP" ]; then
    echo "✅ 헬스 체크 통과!"
    break
  fi
  if [ "$i" -eq 10 ]; then
    echo "❌ 헬스 체크 실패! 배포 중단."
    exit 1
  fi
done

# Nginx 설정 스위칭 (예: symlink나 env 변경 등)
echo "🌈 Nginx 트래픽 전환 ($TARGET_PORT)"
# 예: 심볼릭 링크 전환, nginx config reload 등 수행
# ln -sf /etc/nginx/sites-available/$TARGET.conf /etc/nginx/sites-enabled/default
# nginx -s reload

echo "🧹 이전 컨테이너 정리"
if [ "$TARGET" == "green" ]; then
  docker rm -f $APP_NAME-blue
else
  docker rm -f $APP_NAME-green
fi

echo "✅ 배포 완료: $TARGET"