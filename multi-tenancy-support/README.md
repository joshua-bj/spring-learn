## Dynamic configure OIDC for client
This is a quick example for dynamic configure OIDC providers for client. 
You can use REST API `/config/add-client-registry` add a new client registry.

It also demonstrates as SpringBoot application, how to support multiple tenants which
can be configured for different realm at the Keycloak.

The project can use client id and client secret exchange the JWT from keycloak, then 
use JWT to call resource server's API at [resource-server-auth-by-jwt](../resource-server-auth-by-jwt).

## Development
### how to run this spring boot application
```
./mvnw spring-boot:run
```

### how to run this spring boot application at debug mode
```
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```