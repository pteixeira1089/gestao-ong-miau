package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.EventoPadraoRequestDTO;
import br.org.miauaumigos.backend.dto.EventoPadraoResponseDTO;
import br.org.miauaumigos.backend.service.EventoPadraoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoPadraoController {

    private final EventoPadraoService eventoPadraoService;

    @PostMapping
    public ResponseEntity<EventoPadraoResponseDTO> registrar(@RequestBody @Valid EventoPadraoRequestDTO dto) {
        EventoPadraoResponseDTO evento = eventoPadraoService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(evento);
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<EventoPadraoResponseDTO>> listarPorAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(eventoPadraoService.listarPorAnimal(animalId));
    }
}
