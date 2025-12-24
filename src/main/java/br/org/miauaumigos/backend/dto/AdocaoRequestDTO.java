package br.org.miauaumigos.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdocaoRequestDTO {

    @NotNull(message = "O ID do animal é obrigatório")
    private Long animalId;

    @NotNull(message = "O ID do adotante é obrigatório")
    private Long adotanteId;

    @NotNull(message = "A data da adoção é obrigatória")
    @PastOrPresent(message = "A data da adoção não pode ser no futuro")
    private LocalDate dataAdocao;

    private String observacoes;
}
