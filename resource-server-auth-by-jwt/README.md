## Purpose of this project
This project simulate the resource server which trust the JWT which signed by realm **admin**

The client can use OIDC client's id and secret to exchange JWT, that can be used to invoke the APIs which provided by this server.

This is a simulation of using a fixed functional user for server to server invocation.

## Development
### how to run this spring boot application
```
./mvnw spring-boot:run
```

### how to run this spring boot application at debug mode
```
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
