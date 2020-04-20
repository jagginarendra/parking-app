FROM openjdk:8-jdk-alpine
MAINTAINER Narendra Jaggi
VOLUME /tmp
COPY parkapp.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]