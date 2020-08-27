# search-service
Search Service app to fetch Books and Albums from Google Books API and iTunes API based on the requested text input and it will return maximum of 5 books and maximum of 5 albums that are related to the input term.

Service URL
--
 - http://localhost:8080/search?q=hello
 -- here q can be any search value. 

Swagger URL
--
 -Below Swagger Documentation can help to understand about the Interface
 -http://localhost:8080/swagger-ui/index.html

Metrics URLs
--
 -http://localhost:8080/actuator/metrics (Metrics URL)
 -http://localhost:8080/actuator/metrics/reactor.flow.duration (Upstream service metrics)
 

Technical Info
--
Softwares:
 - Spring Boot 2.3.3 (JDK 11)
 - Maven 3.6.1
 - Swagger 3.0.0
 - Docker

Deployment & Run
--
Manual:
 - Clone the repository from github 'https://github.com/SarathYRMS/search-service.git'
 - mvn clean install
 - Run the spring boot app from target 'java -jar search-service-0.0.1-SNAPSHOT.jar'

Using Docker:
 - mvn clean install
 - Create the docker image by using the command 'docker build -t search-service-app .'
 - Execute the docker image by using the command 'docker run --rm -p8080:8080 search-service-app'
 --- -p8080:8080 is used to expose the application port and map dynamically. 