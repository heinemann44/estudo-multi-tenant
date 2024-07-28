package com.multi_tenant_id.pessoa.json;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Pessoa implements Serializable {

    @JsonProperty("id")
    private UUID id;
    
    @NotNull
    @Length(max = 32)
    @JsonProperty("apelido")
    private String apelido;
    
    @NotNull
    @Length(max = 100)
    @JsonProperty("nome")
    private String nome;
    
    @JsonProperty("nascimento")
    private LocalDate nascimento;
    
    @JsonProperty("stack")
    private List<String> stack;
    
}