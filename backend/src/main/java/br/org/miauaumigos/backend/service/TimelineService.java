package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.EventoTimelineDTO;
import br.org.miauaumigos.backend.model.entity.Adocao;
import br.org.miauaumigos.backend.model.entity.EventoAnimal;
import br.org.miauaumigos.backend.model.entity.EventoPadrao;
import br.org.miauaumigos.backend.repository.EventoAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimelineService {

    private final EventoAnimalRepository eventoAnimalRepository;

    @Transactional(readOnly = true)
    public List<EventoTimelineDTO> listarTimeline(Long animalId) {
        List<EventoAnimal> eventos = eventoAnimalRepository.findByAnimalIdOrderByDataEventoDesc(animalId);
        
        return eventos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarEvento(Long id) {
        eventoAnimalRepository.deleteById(id);
    }

    private EventoTimelineDTO toDTO(EventoAnimal evento) {
        EventoTimelineDTO dto = EventoTimelineDTO.builder()
                .id(evento.getId())
                .dataEvento(evento.getDataEvento())
                .observacoes(evento.getObsEvento())
                .build();

        if (evento instanceof Adocao) {
            Adocao adocao = (Adocao) evento;
            dto.setTipo("ADOCAO");
            dto.setAdotanteId(adocao.getAdotante().getId());
            dto.setNomeAdotante(adocao.getAdotante().getNome());
        } else if (evento instanceof EventoPadrao) {
            EventoPadrao padrao = (EventoPadrao) evento;
            dto.setTipo(padrao.getTipo().name());
        }

        return dto;
    }
}
