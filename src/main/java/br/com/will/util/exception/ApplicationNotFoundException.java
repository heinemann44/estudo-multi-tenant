package br.com.will.util.exception;

public class ApplicationNotFoundException extends ApplicationException {

    public ApplicationNotFoundException() {
        this.status = 404;
    }

}
