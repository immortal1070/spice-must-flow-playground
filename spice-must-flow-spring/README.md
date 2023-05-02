## My Household

### Description

### Implementation

### Setting up the database
```
docker-compose up
```

### Running the application with Maven
You can start the application by running:
```
./mvnw spring-boot:run
```
### Running the application with Docker
First create a docker image with the following command:
```
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=wagawin/my-household
```
Then start the application by running:
```
docker run -p 8080:8080 wagawin/my-household
```
### Running the tests
Have in mind that it is required to have a configured and running database for the load tests. You can run the tests with the following command:
```
./mvnw clean verify
```

### Documentation
After starting the application the documentation can be found here:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Improvements
The following improvements can be done:
* more tests - unit, integration and load testing
* an isolated MySQL server used only in the load tests e.g. with testcontainers.
