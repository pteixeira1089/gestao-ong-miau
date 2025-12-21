package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.AnimalRequestDTO;
import br.org.miauaumigos.backend.dto.AnimalResponseDTO;
import br.org.miauaumigos.backend.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animais")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalResponseDTO> cadastrar(@RequestBody @Valid AnimalRequestDTO dto) {
        AnimalResponseDTO animalSalvo = animalService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalSalvo);
    }

    @GetMapping
    public ResponseEntity<List<AnimalResponseDTO>> listarTodos() {
        return ResponseEntity.ok(animalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AnimalRequestDTO dto) {
        AnimalResponseDTO animalAtualizado = animalService.atualizar(id, dto);
        return ResponseEntity.ok(animalAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        animalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
