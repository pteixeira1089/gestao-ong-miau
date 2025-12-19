package br.org.miauaumigos.backend.dto;

import br.org.miauaumigos.backend.model.enums.EspecieAnimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponseDTO {
    private Long id;
    private String nome;
    private EspecieAnimal especieAnimal;
    private Character sexo;
    private String pelagem;
    private String bio;
    private String urlFoto;
    private LocalDateTime dataCriacao;
}
