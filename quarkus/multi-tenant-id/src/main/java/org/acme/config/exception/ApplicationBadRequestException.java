package org.acme.config.exception;

import org.apache.http.HttpStatus;

public class ApplicationBadRequestException extends ApplicationException{

    public ApplicationBadRequestException() {
        this.status = HttpStatus.SC_BAD_REQUEST;
    }
    
}
