package br.org.miauaumigos.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoTimelineDTO {
    private Long id;
    private String tipo; // "ADOCAO", "VACINA", "ACOLHIMENTO", etc.
    private LocalDate dataEvento;
    private String observacoes;
    
    // Campos específicos
    private Long adotanteId;
    private String nomeAdotante; // Para exibir na tela sem buscar de novo
}
