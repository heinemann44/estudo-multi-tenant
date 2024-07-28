package config.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RestExceptionHandler implements ExceptionMapper<ApplicationException> {
    
    @Override
    public Response toResponse(ApplicationException exception) {
        return Response.status(exception.status).entity(exception.getMessage()).build();	
    }
    
}
