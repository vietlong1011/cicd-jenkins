FROM eclipse-temurin:17-jdk-alpine
LABEL authors="HOANG"
VOLUME /tmp
COPY target/*.jar demo-cicd.jar
ENTRYPOINT ["java", "-jar","demo-cicd.jar"]