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