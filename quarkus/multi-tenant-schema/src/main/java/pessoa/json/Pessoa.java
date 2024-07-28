package pessoa.json;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Pessoa implements Serializable {

    private UUID id;
    
    @NotNull
    @Length(max = 32)
    private String apelido;
    
    @NotNull
    @Length(max = 100)
    private String nome;
    
    private LocalDate nascimento;
    
    private List<String> stack;
    
}