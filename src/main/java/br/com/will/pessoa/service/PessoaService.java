package br.com.will.pessoa.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.will.pessoa.entity.PessoaEntity;
import br.com.will.pessoa.json.Pessoa;
import br.com.will.pessoa.repository.PessoaRepository;
import br.com.will.tenant.TenantContext;
import br.com.will.util.exception.ApplicationBadRequestException;
import br.com.will.util.exception.ApplicationException;
import br.com.will.util.exception.ApplicationNotFoundException;
import br.com.will.util.exception.ApplicationUnprocessableEntityException;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@RequestScoped
public class PessoaService {

    @Inject
    private PessoaRepository repository;

    @Inject
    Validator validator;

    public Pessoa findPersonById(UUID idPessoa) throws ApplicationException {
        PessoaEntity pessoaEntity = this.repository.findByIdOptional(idPessoa)
                .orElseThrow(() -> new ApplicationNotFoundException());

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

    @Transactional
    public void savePerson(Pessoa request) throws ApplicationException {
        this.validate(request);

        try {
            request.setId(UUID.randomUUID());
            PessoaEntity entity = this.getEntity(request);
            this.repository.persistAndFlush(entity);
        } catch (Exception e) {
            throw new ApplicationUnprocessableEntityException();
        }
    }

    private void validate(Pessoa request) throws ApplicationException {
        Set<ConstraintViolation<Pessoa>> validate = this.validator.validate(request);

        if (!validate.isEmpty()) {
            throw new ApplicationUnprocessableEntityException();
        }

        this.validateFormat(request.getNome());

        if (request.getStack() != null && !request.getStack().isEmpty()) {
            for (String stack : request.getStack()) {
                this.validateFormat(stack);
            }
        }

        request.setApelido(request.getApelido() + TenantContext.getTenant());

        PessoaEntity pessoaBD = this.repository.findByApelido(request.getApelido());

        if (pessoaBD != null) {
            throw new ApplicationUnprocessableEntityException();
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
        List<PessoaEntity> allPerson = this.repository.findAllPersonByTerm(term, Page.of(0, 50));

        if (allPerson == null || allPerson.isEmpty()) {
            return new ArrayList<>();
        }

        return allPerson.stream().map(entity -> this.getPessoa(entity)).collect(Collectors.toList());
    }

    public Long countAllPerson() {
        return this.repository.count();
    }

}
