package br.com.will.util.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends ApplicationInterfaceException {

    int status;

    public ApplicationException() {
        this.status = 500;
    }

}
