# 1단계: 빌드
# 🔨 1단계: 빌드
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# 🏁 2단계: 실행용
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /app/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]