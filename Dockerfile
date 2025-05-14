FROM openjdk:17-jdk
LABEL authors="kyuwon"

COPY wayfarer-task-request-api/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
