package pessoa.repository;

import java.util.List;
import java.util.UUID;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;
import pessoa.entity.PessoaEntity;

@RequestScoped
public class PessoaRepository implements PanacheRepositoryBase<PessoaEntity, UUID>{

    public List<PessoaEntity> findAllPersonByTerm(String term, Page pageable){
        PanacheQuery<PessoaEntity> panacheQuery = this.find("buscaTermo ILIKE ?1", "%" + term + "%");

        panacheQuery.page(pageable);

        return panacheQuery.list();
    }

} 


