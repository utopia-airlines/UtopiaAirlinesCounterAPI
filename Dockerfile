FROM openjdk:8-jdk-alpine
WORKDIR /
COPY target/conter-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Dutopia.search.API=$1 -Dutopia.booking.API=$2 -Dutopia.cancellation.API=$3 -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
