package com.multi_tenant_schema.pessoa.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.multi_tenant_schema.pessoa.entity.PessoaEntity;

public interface PessoaRepository  extends JpaRepository<PessoaEntity, UUID>{

    @Query("FROM PessoaEntity WHERE buscaTermo ILIKE %:term%")
    List<PessoaEntity> findAllPersonByTerm(@Param("term") String term, Pageable pageable);
    
    @Query("FROM PessoaEntity WHERE id = :id")
    Optional<PessoaEntity> findById(UUID id);

    @Query(nativeQuery =  true, value = "select count(*) from pessoas;")
    long count();

} 


