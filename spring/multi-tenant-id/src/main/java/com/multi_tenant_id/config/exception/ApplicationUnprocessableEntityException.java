package com.multi_tenant_id.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ApplicationUnprocessableEntityException extends ApplicationException{
    
}
