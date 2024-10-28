package br.com.will.interceptor;

import jakarta.ws.rs.core.Response.Status;

public class WebServiceException extends
        RuntimeException {

    private Status status = Status.BAD_REQUEST;

    public WebServiceException() {
    }

    public WebServiceException(String message) {
        super(message);
    }

    public WebServiceException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceException(Throwable cause) {
        super(cause);
    }

    public WebServiceException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
