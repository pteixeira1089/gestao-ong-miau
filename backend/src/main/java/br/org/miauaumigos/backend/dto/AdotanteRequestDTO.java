package br.org.miauaumigos.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class AdotanteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 500)
    private String nome;

    @Size(max = 100)
    private String telefone;

    @Email(message = "E-mail inválido")
    @Size(max = 500)
    private String email;

    @NotBlank(message = "O documento de identificação (CPF/RG) é obrigatório")
    @Size(max = 255)
    private String documentoIdentificacao;

    @NotNull(message = "O endereço é obrigatório")
    @Valid // Importante: Valida os campos dentro do EnderecoDTO
    private EnderecoDTO endereco;

    @Size(max = 2000)
    private String observacoes;

    private String fotoUrl;
}
