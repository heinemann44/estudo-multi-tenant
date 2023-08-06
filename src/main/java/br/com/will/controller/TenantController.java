package br.com.will.controller;

import java.util.List;

import br.com.will.dto.TenantDTO;
import br.com.will.service.TenantService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tenantController")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TenantController {

    @HeaderParam(value = "x-tenant")
    String tenant;

    @Inject
    TenantService tenantService;

    @POST
    @Path("/save")
    public Response save(TenantDTO tenantDTO) {
        tenantService.save(tenantDTO);

        return Response.ok().build();
    }

    @GET
    @Path("/listAll")
    public List<TenantDTO> listAll() {

        return tenantService.listAll();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        tenantService.delete(id);

        return Response.ok().build();
    }

}
