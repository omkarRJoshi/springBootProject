package com.springproject.springboot.learning.error;

import com.springproject.springboot.learning.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // whatever class that we create to handle exceptions, that should be annotated with @ControllerAdvice
@ResponseStatus // as it sends the response status
// this is the class that will handle the all the particular exceptions that we want to send back as a response entity
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DepartmentNotFoundException.class) // what type of exception this particular method is handling
    // method which handles "DepartmentNotFoundException", and send the response as the request with the message
    public ResponseEntity<ErrorMessage> departmentNotFoundException(DepartmentNotFoundException exception, WebRequest request){
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
