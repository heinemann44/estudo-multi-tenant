package com.multi_tenant_schema.config.exception;

public abstract class ApplicationInterfaceException extends Exception{
 
    public ApplicationInterfaceException(){
        super();
    }

    public ApplicationInterfaceException(String message){
        super(message);
    }

}
