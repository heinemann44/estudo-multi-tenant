package br.com.will.pessoa.repository;

import java.util.List;
import java.util.UUID;

import br.com.will.pessoa.entity.PessoaEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class PessoaRepository implements PanacheRepositoryBase<PessoaEntity, UUID> {

    public PessoaEntity findByApelido(String apelido) {
        PanacheQuery<PessoaEntity> panacheQuery = this.find("apelido = ?1", apelido);

        return panacheQuery.firstResult();
    }

    public List<PessoaEntity> findAllPersonByTerm(String term, Page pageable) {
        PanacheQuery<PessoaEntity> panacheQuery = this.find("buscaTermo ILIKE ?1", "%" + term + "%");

        panacheQuery.page(pageable);

        return panacheQuery.list();
    }

}
