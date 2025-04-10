# 1단계: 빌드
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY build/libs/*SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
ENTRYPOINT ["/bin/bash", "-c", "sleep 500"]