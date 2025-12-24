package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.AdotanteRequestDTO;
import br.org.miauaumigos.backend.dto.AdotanteResponseDTO;
import br.org.miauaumigos.backend.service.AdotanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adotantes")
@RequiredArgsConstructor
public class AdotanteController {

    private final AdotanteService adotanteService;

    @PostMapping
    public ResponseEntity<AdotanteResponseDTO> cadastrar(@RequestBody @Valid AdotanteRequestDTO dto) {
        AdotanteResponseDTO adotanteSalvo = adotanteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(adotanteSalvo);
    }

    @GetMapping
    public ResponseEntity<List<AdotanteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(adotanteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdotanteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(adotanteService.buscarPorId(id));
    }
}
