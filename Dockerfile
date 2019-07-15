FROM openjdk:8-jdk-alpine
WORKDIR /
COPY conter-0.0.1-SNAPSHOT.jar app.jar
COPY script.sh script.sh
EXPOSE 8080
ENTRYPOINT [ "sh","./script.sh" ]
