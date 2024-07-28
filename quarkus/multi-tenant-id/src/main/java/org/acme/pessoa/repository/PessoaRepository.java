package org.acme.pessoa.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.acme.pessoa.entity.PessoaEntity;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class PessoaRepository implements PanacheRepositoryBase<PessoaEntity, UUID>{

    public List<PessoaEntity> findAllPersonByTerm(String term, Page pageable){
        PanacheQuery<PessoaEntity> panacheQuery = this.find("buscaTermo ILIKE ?1", "%" + term + "%");

        panacheQuery.page(pageable);

        return panacheQuery.list();
    }

    public Optional<PessoaEntity> findByIdOpt(UUID id){
        return Optional.ofNullable(this.find("id", id).firstResult());
    }

} 


