package org.acme.config.exception;

import org.apache.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends ApplicationInterfaceException {

    int status;

    public ApplicationException() {
        this.status = HttpStatus.SC_INTERNAL_SERVER_ERROR;
    }

}
