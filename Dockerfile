FROM eclipse-temurin:17-jdk-alpine
LABEL authors="LONG"
VOLUME /tmp
COPY target/*.jar demo-cicd-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","demo-cicd-0.0.1-SNAPSHOT.jar"]