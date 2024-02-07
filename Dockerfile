FROM maven:3.8.4-openjdk-17 as build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app

RUN mvn clean package

FROM openjdk:17-jdk-slim

ENV CACERTS_PATH=/certificates/cacerts

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dmicronaut.environments=local", "-Djavax.net.ssl.trustStore=${CACERTS_PATH}", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "/app/app.jar"]