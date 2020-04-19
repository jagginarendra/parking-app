FROM openjdk:8-jdk-alpine
MAINTAINER Narendra Jaggi
ENTRYPOINT ["java","-jar","/parking-app-0.0.1-SNAPSHOT.jar"]
EXPOSE 9080