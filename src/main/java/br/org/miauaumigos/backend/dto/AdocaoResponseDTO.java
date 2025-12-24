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
public class AdocaoResponseDTO {
    private Long id;
    private AnimalResponseDTO animal;
    private AdotanteResponseDTO adotante;
    private LocalDate dataAdocao;
    private String observacoes;
}
