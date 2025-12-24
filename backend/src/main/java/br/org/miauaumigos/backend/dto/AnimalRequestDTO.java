package br.org.miauaumigos.backend.dto;

import br.org.miauaumigos.backend.model.enums.EspecieAnimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalRequestDTO {

    @NotBlank(message = "O nome do animal é obrigatório")
    @Size(max = 255, message = "O nome deve ter no máximo 255 caracteres")
    private String nome;

    @NotNull(message = "A espécie do animal é obrigatória")
    private EspecieAnimal especieAnimal;

    @NotNull(message = "O sexo do animal é obrigatório")
    private Character sexo;

    @NotBlank(message = "A pelagem/cor é obrigatória")
    private String pelagem;

    @Size(max = 2000, message = "A bio deve ter no máximo 2000 caracteres")
    private String bio;

    private String urlFoto;
}
