FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY target/file-watcher-service-0.0.1-SNAPSHOT.jar app.jar
ENV SERVER_PORT=8082
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]

