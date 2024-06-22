# CashCards - Restful API Project

## Overview

Implemented a robust Restful API project "CashCards" utilizing Java Spring, focused on secure financial transactions. This project showcases a variety of key features and best practices in modern software development.

## Features

- **Test-Driven Development (TDD)**: Applied TDD principles to ensure code reliability and quality through rigorous testing.
- **Database Management**: Managed database version control with Flyway migrations and integrated MySQL for efficient data handling.
- **Security**: Designed and enforced role-based authentication and authorization mechanisms to safeguard user information and ensure proper access control.
- **Error Handling**: Emphasized comprehensive error handling to enhance system stability and user experience.
- **Dockerized Solution**: Provided a Dockerized solution to streamline deployment and ensure consistency across environments.

## Technologies Used

- Java Spring
- MySQL
- Flyway
- Docker
- JWT for Authentication and Authorization

## Notes

### POST, PUT, PATCH and CRUD Operations - Summary
![img.png](img.png)

### DELETE - Summary
![img_1.png](img_1.png)

### FlyWay migrate
```commandline
 mvn clean flyway:migrate
```

### @ResponseStatus vs @ExceptionHandler vs @ControllerAdvice

#### @ExceptionHandler & @ResponseStatus & REST
```java
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyBadDataException.class)
    @ResponseBody ErrorInfo
    handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
```

#### @ResponseStatus
```java
@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="User with such username already exists")  // 401
public class CustomUserAlreadyExistsStatus extends RuntimeException {
}
```

#### @ControllerAdvice
```java
@ControllerAdvice
public class CustomUserAlreadyExistsException
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict() {
        String bodyOfResponse = "Error from controller advice";
        return new ResponseEntity<>(bodyOfResponse,
                new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
```

### Docker

#### Running
```commandline
sudo docker-compose up -d
```
> **_NOTE:_**  d - optional detached mode.
#### Connecting
connection from CL:
```commandline
mysql -P 3307  -u user -h 127.0.0.1 -p
```
OR
```commandline
mysql -P 3307 --protocol=tcp -u user -p
```
> **_NOTE:_**  localhost as a host won't work.
 
#### Operations with containers
To stop all containers:
```commandline
sudo docker stop $(sudo docker ps -aq)
```
To remove all containers:
```commandline
sudo docker rm $(sudo docker ps -aq)
```

#### Cleaning
To remove unused volumes:
```commandline
sudo docker volume prune
```
#### Building
Pulling mysql image:
```commandline
sudo docker pull mysql
```
Running mysql image:
```commandline
sudo docker run -p 3307:3306 --name mysqlcontainer -e MYSQL_ROOT_PASSWORD=rootroot -e MYSQL_DATABASE=CashCardDB -d mysql
```
Creating a network for containers and connecting to it:
```commandline
sudo docker network create networkmysql
sudo docker network connect networkmysql mysqlcontainer 
```
> **_NOTE:_** Clean and install your Spring project.

Building image from Dockerfile:
```commandline
sudo docker build -t crudimage .
```
Running the program image:
```commandline
sudo docker run -p 8090:8080 --name crudcontainer --net networkmysql -e MYSQL_HOST=mysqlcontainer -e MYSQL_PORT=3306 -e MYSQL_DB_NAME=CashCardDB -e MYSQL_USER=root -e MYSQL_PASSWORD=rootroot crudimage
```
### Error Handling
![img_2.png](img_2.png)
Add maven dependency:
```xml
<dependency>
   <groupId>com.fasterxml.jackson.datatype</groupId>
   <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```
Jackson JSR310 converters. They convert Java date and time classes to JSON representation using the @JsonFormat annotation

## Resources:
Error Handling: 
* https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-advice.html
* https://www.toptal.com/java/spring-boot-rest-api-error-handling
* https://www.baeldung.com/exception-handling-for-rest-with-spring

Dockerization:
* https://www.youtube.com/watch?v=thLhAhPx8ww

CashCards Project:
* https://spring.academy/courses/building-a-rest-api-with-spring-boot

Spring Security (JWT):
* https://www.youtube.com/watch?v=TeBt0Ike_Tk
