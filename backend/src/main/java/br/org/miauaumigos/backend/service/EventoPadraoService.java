package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.EventoPadraoRequestDTO;
import br.org.miauaumigos.backend.dto.EventoPadraoResponseDTO;
import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.model.entity.EventoPadrao;
import br.org.miauaumigos.backend.repository.AnimalRepository;
import br.org.miauaumigos.backend.repository.EventoPadraoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoPadraoService {

    private final EventoPadraoRepository eventoPadraoRepository;
    private final AnimalRepository animalRepository;

    @Transactional
    public EventoPadraoResponseDTO registrar(EventoPadraoRequestDTO dto) {
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + dto.getAnimalId()));

        EventoPadrao evento = new EventoPadrao();
        evento.setAnimal(animal);
        evento.setTipo(dto.getTipoEvento());
        evento.setDataEvento(dto.getDataEvento());
        evento.setObsEvento(dto.getObservacoes());

        EventoPadrao eventoSalvo = eventoPadraoRepository.save(evento);
        return toResponseDTO(eventoSalvo);
    }

    @Transactional(readOnly = true)
    public List<EventoPadraoResponseDTO> listarPorAnimal(Long animalId) {
        return eventoPadraoRepository.findByAnimalIdOrderByDataEventoDesc(animalId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private EventoPadraoResponseDTO toResponseDTO(EventoPadrao entity) {
        return EventoPadraoResponseDTO.builder()
                .id(entity.getId())
                .animalId(entity.getAnimal().getId())
                .nomeAnimal(entity.getAnimal().getNome())
                .tipoEvento(entity.getTipo())
                .dataEvento(entity.getDataEvento())
                .observacoes(entity.getObsEvento())
                .build();
    }
}
