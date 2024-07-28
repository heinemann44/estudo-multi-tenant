package com.multi_tenant_id.config.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<String> handleInternalError(ApplicationException exception){
        return new ResponseEntity<>(this.getHttpStatus(exception));
    }

    private HttpStatus getHttpStatus(ApplicationException exception){
        return exception.getClass().getAnnotation(ResponseStatus.class).value();
    }
    
}
