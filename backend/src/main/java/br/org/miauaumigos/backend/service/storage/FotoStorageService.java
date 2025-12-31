package br.org.miauaumigos.backend.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorageService {
    /**
     * Armazena o arquivo e retorna o nome final gerado
     */
    String armazenar(MultipartFile file);

    /**
     * Retorna a URL completa para acessar a foto
     */
    String recuperarUrl(String fileName);
}
