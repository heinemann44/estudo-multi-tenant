package config.exception;

public class ApplicationNotFoundException extends ApplicationException{

    public ApplicationNotFoundException() {
        this.status = org.apache.http.HttpStatus.SC_NOT_FOUND;
    }
    
}
