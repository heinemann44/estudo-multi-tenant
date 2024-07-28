package com.multi_tenant_schema.pessoa.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.multi_tenant_schema.config.database.TenantContext;
import com.multi_tenant_schema.config.exception.ApplicationBadRequestException;
import com.multi_tenant_schema.config.exception.ApplicationException;
import com.multi_tenant_schema.config.exception.ApplicationNotFoundException;
import com.multi_tenant_schema.config.exception.ApplicationUnprocessableEntityException;
import com.multi_tenant_schema.pessoa.entity.PessoaEntity;
import com.multi_tenant_schema.pessoa.json.Pessoa;
import com.multi_tenant_schema.pessoa.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa findPersonById(UUID idPessoa) throws ApplicationException {
        PessoaEntity pessoaEntity = this.repository.findById(idPessoa).orElseThrow(()-> new ApplicationNotFoundException());

        return this.getPessoa(pessoaEntity);
    }

    private Pessoa getPessoa(PessoaEntity pessoaEntity) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaEntity.getId());
        pessoa.setApelido(pessoaEntity.getApelido());
        pessoa.setNome(pessoaEntity.getNome());
        pessoa.setNascimento(LocalDate.parse(pessoaEntity.getNascimento()));
        pessoa.setStack(pessoaEntity.getStack());
        return pessoa;
    }

    public void savePerson(Pessoa request) throws ApplicationException {
        this.validate(request);

        request.setApelido(request.getApelido() + TenantContext.getCurrentTenant());

        try{
            request.setId(UUID.randomUUID());
            PessoaEntity entity = this.getEntity(request);
            this.repository.save(entity);
        } catch( DataIntegrityViolationException e ){
            throw new ApplicationUnprocessableEntityException();
        }
    }

    private void validate(Pessoa request) throws ApplicationException {
        Set<ConstraintViolation<Pessoa>> validate = Validation.buildDefaultValidatorFactory().getValidator().validate(request);

        if(!validate.isEmpty()){
            throw new ApplicationUnprocessableEntityException();
        }
        
        this.validateFormat(request.getNome());
        
        if(request.getStack() != null && !request.getStack().isEmpty()){
            for (String stack : request.getStack()) {
                this.validateFormat(stack);
            }
        }
    }

    private void validateFormat(String text) throws ApplicationBadRequestException {
        try {
            Long.parseLong(text);
            
            throw new ApplicationBadRequestException();
        } catch (NumberFormatException e) {
            // Se deu erro ao converter então o texto não é número
        }
    }

    private PessoaEntity getEntity(Pessoa request) {
        PessoaEntity entity = new PessoaEntity();

        entity.setId(request.getId());
        entity.setNome(request.getNome());
        entity.setApelido(request.getApelido());
        entity.setNascimento(request.getNascimento().toString());
        entity.setStack(request.getStack());

        return entity;
    }

    public List<Pessoa> findAllPersonByTerm(String term) throws ApplicationNotFoundException {
        List<PessoaEntity> allPerson = this.repository.findAllPersonByTerm(term,  PageRequest.of(0, 50));

        if(allPerson == null || allPerson.isEmpty()){
            return new ArrayList<>();
        }

        return allPerson.stream().map(entity -> this.getPessoa(entity)).collect(Collectors.toList());
    }

    public Long countAllPerson() {
        return this.repository.count();
    }
    
}
