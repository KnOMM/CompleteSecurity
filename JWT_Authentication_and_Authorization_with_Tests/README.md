# Useful commands
```commandline
docker-compose up -d
```
then wait and login (localhost:8081) with credentials from docker-compose.yml file - basic section

## Disabling default behaviour of GrantedAuthorities bean
ROLE_ prefix now won't be added.
```java
@Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }
```
