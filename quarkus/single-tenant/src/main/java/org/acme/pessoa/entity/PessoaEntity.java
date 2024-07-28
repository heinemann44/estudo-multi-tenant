package org.acme.pessoa.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaEntity {
    
    @Id
    private UUID id;

    @Column(name = "apelido")
    private String apelido;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nascimento")
    private String nascimento;

    @Convert(converter = StringListConverter.class)
    @Column(name = "stack")
    private List<String> stack;

    @Column(name = "BUSCA_TRGM", insertable = false, updatable = false)
    private String buscaTermo;
}