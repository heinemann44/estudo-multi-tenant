package br.com.will.util.exception;

public class ApplicationBadRequestException extends ApplicationException {

    public ApplicationBadRequestException() {
        this.status = 400;
    }

}
