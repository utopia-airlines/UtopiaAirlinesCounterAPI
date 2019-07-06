FROM openjdk
WORKDIR /
ADD target/conter-0.0.1-SNAPSHOT.jar conter-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar conter-0.0.1-SNAPSHOT.jar
