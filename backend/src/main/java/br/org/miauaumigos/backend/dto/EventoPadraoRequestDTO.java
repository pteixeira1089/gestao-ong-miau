package br.org.miauaumigos.backend.dto;

import br.org.miauaumigos.backend.model.enums.TipoEventoAnimal;
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
public class EventoPadraoRequestDTO {

    @NotNull(message = "O ID do animal é obrigatório")
    private Long animalId;

    @NotNull(message = "O tipo do evento é obrigatório")
    private TipoEventoAnimal tipoEvento;

    @NotNull(message = "A data do evento é obrigatória")
    @PastOrPresent(message = "A data do evento não pode ser no futuro")
    private LocalDate dataEvento;

    private String observacoes;
}
