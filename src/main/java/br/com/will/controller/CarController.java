package br.com.will.controller;

import java.util.List;

import br.com.will.dto.CarDTO;
import br.com.will.service.CarService;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.RunOnVirtualThread;
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

@Path("carController")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarController {

    @HeaderParam(value = "x-tenant")
    String tenant;

    @Inject
    CarService carService;

    @POST
    @RunOnVirtualThread
    @Path("save")
    public Response save(CarDTO carDTO) {
        carService.save(carDTO);
        Log.info(Thread.currentThread());
        return Response.ok().build();
    }

    @GET
    @RunOnVirtualThread
    @Path("listAll")
    public List<CarDTO> listAll() {
        Log.info(Thread.currentThread());
        return carService.listAll();
    }

    @DELETE
    @RunOnVirtualThread
    @Path("delete/{id}")
    public Response delete(@PathParam("id") Long id) {
        carService.delete(id);

        return Response.ok().build();
    }

}
