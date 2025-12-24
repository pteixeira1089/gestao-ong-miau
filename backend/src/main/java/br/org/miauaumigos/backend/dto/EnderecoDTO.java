package br.org.miauaumigos.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {

    @NotBlank(message = "O logradouro é obrigatório")
    @Size(max = 255)
    private String logradouro;

    @NotBlank(message = "O número é obrigatório")
    @Size(max = 10)
    private String numero;

    @Size(max = 255)
    private String complemento;

    @Size(max = 255)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "O estado/UF é obrigatório")
    @Size(min = 2, max = 2, message = "O estado/uf deve ser a sigla (ex: SP)")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "O CEP deve estar no formato 00000-000")
    private String cep;
}
