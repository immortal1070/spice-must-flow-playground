# recipes-kotlin-spring-ddd
This project contains an example of a REST web service based on [Domain Driven Design](https://learn.microsoft.com/en-us/dotnet/architecture/microservices/microservice-ddd-cqrs-patterns/ddd-oriented-microservice). 
The goal is to give an example of how "pure DDD" project may look like. It's intentionally has all the 
possible layers and classes.

The project holds 2 objects:
* ingredient
* recipe - can hold a collection of recipe ingredients with a specified amount

Approaches used in this project
* DDD-lite structure based on [Implementing Domain-Driven Design](https://www.oreilly.com/library/view/implementing-domain-driven-design/9780133039900/)
* REST API designed with CQRS
* Stereotypes are used to untie project from a DI framework
* Component tests use clients and validators - to isolate the tests logic from the implementation details

## Running the application
### Dev mode
You can start the application by running:
```
./gradlew bootRun
```
And then check available REST endpoints at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Build and run with Docker
First create a docker image with the following command:
```
./gradlew bootBuildImage --imageName=immortal/recipes
``` 
Then start the application by running:
```
docker run -p 8080:8080 -e POSTGRES_HOST=host.docker.internal --add-host=host.docker.internal:host-gateway immortal/recipes
```
or with docker-compose:
```
docker-compose -f ./docker/docker-compose.yaml up recipes
```

## Tests
Service is covered with Component tests which test the REST endpoints using RestAssured and mocking database with TestContainers. 
### Running the tests
```
./gradlew clean test --info
```
### Running tests over local service and database
One can comment next lines:
```
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
