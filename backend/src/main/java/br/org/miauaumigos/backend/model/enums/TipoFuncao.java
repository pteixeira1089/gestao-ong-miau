package br.org.miauaumigos.backend.model.enums;

import lombok.Getter;

import java.text.Normalizer;
import java.util.Arrays;

@Getter
public enum TipoFuncao {
    VETERINARIO(true, "Veterinário"),
    ADMINISTRADOR(false, "Administrador");

    private final boolean podeEmitirPrescricao;
    private final String nomeAmigavel;

    TipoFuncao(boolean podeEmitirPrescricao, String nomeAmigavel) {
        this.podeEmitirPrescricao = podeEmitirPrescricao;
        this.nomeAmigavel = nomeAmigavel;
    }

    public static TipoFuncao fromNomeAmigavel(String nome) {
        String nomeSemAcento = Normalizer.normalize(nome, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", ""); // Remove acentos

        return Arrays.stream(values())
                .filter(e ->
                        Normalizer.normalize(e.nomeAmigavel, Normalizer.Form.NFD)
                                .replaceAll("[^\\p{ASCII}]", "")
                                .equalsIgnoreCase(nomeSemAcento)
                )
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo inválido: " + nome));
    }
}
