package br.com.will.interceptor;

import br.com.will.tenant.TenantContext;
import io.quarkus.logging.Log;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionInterceptor implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception ex) {

		// Must clear to avoid memory leak
		TenantContext.clear();

		if (ex instanceof WebServiceException exception) {
			Log.errorv("ERRO --> {0}", exception.getMessage());
			return Response.status(exception.getStatus()).entity(new ReponseErrorMessage(exception.getMessage()))
					.type(MediaType.APPLICATION_JSON).build();
		}

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ReponseErrorMessage(ex.getMessage()))
				.type(MediaType.APPLICATION_JSON)
				.build();

	}
}
