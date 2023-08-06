package br.com.will.controller;

import java.util.List;

import br.com.will.dto.CarDTO;
import br.com.will.service.CarService;
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

@Path("/carController")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarController {

    @HeaderParam(value = "x-tenant")
    String tenant;

    @Inject
    CarService carService;

    @POST
    @Path("/save")
    public Response save(CarDTO carDTO) {
        carService.save(carDTO);

        return Response.ok().build();
    }

    @GET
    @Path("/listAll")
    public List<CarDTO> listAll() {

        return carService.listAll();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        carService.delete(id);

        return Response.ok().build();
    }

}
