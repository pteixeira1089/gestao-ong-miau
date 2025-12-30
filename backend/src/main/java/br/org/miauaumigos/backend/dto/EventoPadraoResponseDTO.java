package br.org.miauaumigos.backend.dto;

import br.org.miauaumigos.backend.model.enums.TipoEventoAnimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoPadraoResponseDTO {
    private Long id;
    private Long animalId;
    private String nomeAnimal;
    private TipoEventoAnimal tipoEvento;
    private LocalDate dataEvento;
    private String observacoes;
}
