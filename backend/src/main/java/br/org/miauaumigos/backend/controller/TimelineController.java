package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.EventoTimelineDTO;
import br.org.miauaumigos.backend.service.TimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final TimelineService timelineService;

    @GetMapping("/{animalId}")
    public ResponseEntity<List<EventoTimelineDTO>> listarTimeline(@PathVariable Long animalId) {
        return ResponseEntity.ok(timelineService.listarTimeline(animalId));
    }

    @DeleteMapping("/{eventoId}")
    public ResponseEntity<Void> deletarEvento(@PathVariable Long eventoId) {
        timelineService.deletarEvento(eventoId);
        return ResponseEntity.noContent().build();
    }
}
