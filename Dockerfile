FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/search-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} /home/service/search-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/service/search-service.jar"]