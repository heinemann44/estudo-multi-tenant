package config.exception;

import org.apache.http.HttpStatus;

public class ApplicationUnprocessableEntityException extends ApplicationException{

    public ApplicationUnprocessableEntityException() {
        this.status = HttpStatus.SC_UNPROCESSABLE_ENTITY;
    }
    
}
