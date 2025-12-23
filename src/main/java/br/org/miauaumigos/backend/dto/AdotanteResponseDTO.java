package br.org.miauaumigos.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdotanteResponseDTO {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String documentoIdentificacao;
    private EnderecoDTO endereco;
    private String observacoes;
    private String fotoUrl;
    private LocalDateTime dataCriacao;
}
