FROM openjdk:8-jdk-alpine
MAINTAINER Narendra Jaggi
ENTRYPOINT ["java","-jar","/parking-app.jar"]
EXPOSE 9080