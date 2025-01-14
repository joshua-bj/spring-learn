## Development 
### how to run this spring boot application
```
mvn spring-boot:run
```

### how to run this spring boot application at debug mode
```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```