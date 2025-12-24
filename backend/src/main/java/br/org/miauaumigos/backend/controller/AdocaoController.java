package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.AdocaoRequestDTO;
import br.org.miauaumigos.backend.dto.AdocaoResponseDTO;
import br.org.miauaumigos.backend.service.AdocaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adocoes")
@RequiredArgsConstructor
public class AdocaoController {

    private final AdocaoService adocaoService;

    @PostMapping
    public ResponseEntity<AdocaoResponseDTO> registrar(@RequestBody @Valid AdocaoRequestDTO dto) {
        AdocaoResponseDTO adocao = adocaoService.registrarAdocao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(adocao);
    }

    @GetMapping
    public ResponseEntity<List<AdocaoResponseDTO>> listarTodas() {
        return ResponseEntity.ok(adocaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdocaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(adocaoService.buscarPorId(id));
    }
}
