FROM eclipse-temurin:17-jdk-alpine
LABEL authors="LONG"
VOLUME /tmp
COPY demo-cicd/*.jar demo-cicd.jar
ENTRYPOINT ["java", "-jar","demo-cicd.jar"]