package br.com.will.pessoa.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import br.com.will.pessoa.json.Pessoa;
import br.com.will.pessoa.service.PessoaService;
import br.com.will.util.exception.ApplicationBadRequestException;
import br.com.will.util.exception.ApplicationException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class PessoaController {

    @Inject
    private PessoaService service;

    @Path("/pessoas/{idPessoa}")
    @GET
    public Pessoa findPersonById(@PathParam("idPessoa") final UUID id) throws ApplicationException {
        return this.service.findPersonById(id);
    }

    @Path("/pessoas")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePerson(Pessoa request) throws ApplicationException {
        this.service.savePerson(request);

        return Response.created(URI.create("/pessoas/" + request.getId())).build();
    }

    @Path("/pessoas")
    @GET
    public List<Pessoa> findByTerm(@QueryParam(value = "t") String term) throws ApplicationException {
        if (term == null) {
            throw new ApplicationBadRequestException();
        }

        return this.service.findAllPersonByTerm(term);
    }

    @Path("/contagem-pessoas")
    @GET
    public Long countAllPerson() {
        return this.service.countAllPerson();
    }

}