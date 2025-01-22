## Dynamic configure OIDC for client
This is a quick example for dynamic configure a OIDC provider for client, can retrieve OIDC server configuration from a database.

## Development
### how to run this spring boot application
```
./mvnw spring-boot:run
```

### how to run this spring boot application at debug mode
```
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```