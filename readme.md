## Two Providers
This is the project clone from https://github.com/spring-guides/tut-spring-boot-oauth2/tree/main/two-providers

As it needs connect with KeyCloak 26.0.7, the original spring boot version `2.2.2.RELEASE` need upgrade to `3.0.4`. Or it will encount a JSON parse exception:
```
Unexpected type of JSON object member with key "mtls_endpoint_aliases"
```

