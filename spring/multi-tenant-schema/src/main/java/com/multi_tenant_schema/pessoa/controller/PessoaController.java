package com.multi_tenant_schema.pessoa.controller;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.multi_tenant_schema.config.exception.ApplicationException;
import com.multi_tenant_schema.pessoa.json.Pessoa;
import com.multi_tenant_schema.pessoa.service.PessoaService;

@RestController
@RequestMapping
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping(value = "/pessoas/{idPessoa}")
    public Pessoa findPersonById(@PathVariable("idPessoa") final UUID id ) throws ApplicationException {
        return this.service.findPersonById(id);
    }
    
    @PostMapping("/pessoas")
    public ResponseEntity<Pessoa> savePerson(@RequestBody Pessoa request) throws ApplicationException {
        this.service.savePerson(request);

        return ResponseEntity.created(URI.create("/pessoas/" + request.getId())).build();
    }

    @GetMapping("/pessoas")
    public List<Pessoa> findByTerm(@RequestParam(value = "t") String term) throws ApplicationException {
        return this.service.findAllPersonByTerm(term);
    }

    @GetMapping("/contagem-pessoas")
    public Long countAllPerson() {
        return this.service.countAllPerson();
    }
}