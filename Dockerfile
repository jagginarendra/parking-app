FROM openjdk:8-jdk-alpine
MAINTAINER Narendra Jaggi
VOLUME /tmp
COPY parking-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]