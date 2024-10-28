package br.com.will.util.exception;

public class ApplicationUnprocessableEntityException extends ApplicationException {

    public ApplicationUnprocessableEntityException() {
        this.status = 422;
    }

}
