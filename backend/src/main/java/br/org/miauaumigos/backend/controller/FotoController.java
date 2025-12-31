package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.service.storage.FotoStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/fotos")
@CrossOrigin(origins = "*") // Importante para o React conseguir acessar depois
public class FotoController {
    private final FotoStorageService fotoStorageService;

    public FotoController(FotoStorageService fotoStorageService) {
        this.fotoStorageService = fotoStorageService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFoto(@RequestParam("arquivo") MultipartFile arquivo) {
        // 1. Chama nosso serviço blindado para salvar
        String nomeArquivo = fotoStorageService.armazenar(arquivo);

        // 2. Recupera a URL completa para visualização
        String url = fotoStorageService.recuperarUrl(nomeArquivo);

        // 3. Retorna um JSON com a URL (o Frontend vai precisar disso)
        return ResponseEntity.ok(Map.of(
                "nomeArquivo", nomeArquivo,
                "url", url
        ));
    }
}
