FROM ubuntu:latest

ENTRYPOINT ["top", "-b"]

FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/core-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} docker-springboot.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/docker-springboot.jar"]
