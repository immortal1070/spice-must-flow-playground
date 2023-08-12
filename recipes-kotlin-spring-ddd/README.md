# spice-must-flow
This project contains an example a REST web service built with Kotlin and Spring based on DDD principles and best practices.

The project holds 2 simple Domain Objects:
* ingredient
* recipe - can hold a collection of recipe ingredients with a specified amount

## Running the application
### dev mode
You can start the application by running:
```
./gradlew bootRun
```
And then check available REST endpoints at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### build and run with Docker
First create a docker image with the following command:
```
./gradlew bootBuildImage --imageName=immortal/spice-must-flow
``` 
Then start the application by running:
```
docker run -p 8080:8080 -e POSTGRES_HOST=host.docker.internal --add-host=host.docker.internal:host-gateway immortal/spice-must-flow
```
or with docker-compose:
```
docker-compose -f ./docker/docker-compose.yaml up spice-must-flow
```

## Tests
Service is covered with Component tests which test the REST endpoints using RestAssured and mocking database with TestContainers. 
### Running the tests
```
./gradlew clean test --info
```
### Running tests over local service and database
One can comment next lines:
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithDatabase
```
To run tests over the local service and database.
Related configuration of database can be found in `application.properties` file.

## Stack
* Kotlin
* Spring
* Gradle
* JPA
* Liquibase
* PostgreSQL
* RestAssured
