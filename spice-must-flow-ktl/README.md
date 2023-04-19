# spice-must-flow

### Running the application with Gradle
You can start the application by running:
```
./gradlew bootRun
```
### Running the application with Docker
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
### Running the tests
```
./gradlew clean test --info
```

### Documentation
After starting the application the documentation can be found here:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
