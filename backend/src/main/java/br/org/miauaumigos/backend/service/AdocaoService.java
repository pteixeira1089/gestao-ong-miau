package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.*;
import br.org.miauaumigos.backend.model.entity.Adocao;
import br.org.miauaumigos.backend.model.entity.Adotante;
import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.model.entity.EventoAnimal;
import br.org.miauaumigos.backend.repository.AdocaoRepository;
import br.org.miauaumigos.backend.repository.AdotanteRepository;
import br.org.miauaumigos.backend.repository.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdocaoService {

    private final AdocaoRepository adocaoRepository;
    private final AnimalRepository animalRepository;
    private final AdotanteRepository adotanteRepository;

    @Transactional
    public AdocaoResponseDTO registrarAdocao(AdocaoRequestDTO dto) {
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com ID: " + dto.getAnimalId()));

        // Validação: Verificar se o animal JÁ está adotado
        if (isAnimalAdotado(animal)) {
            throw new IllegalStateException("Este animal já está adotado! Registre uma devolução antes de nova adoção.");
        }

        Adotante adotante = adotanteRepository.findById(dto.getAdotanteId())
                .orElseThrow(() -> new EntityNotFoundException("Adotante não encontrado com ID: " + dto.getAdotanteId()));

        Adocao adocao = new Adocao();
        adocao.setAnimal(animal);
        adocao.setAdotante(adotante);
        adocao.setDataEvento(dto.getDataAdocao());
        adocao.setObsEvento(dto.getObservacoes());

        Adocao adocaoSalva = adocaoRepository.save(adocao);

        return toResponseDTO(adocaoSalva);
    }

    @Transactional(readOnly = true)
    public List<AdocaoResponseDTO> listarTodas() {
        return adocaoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AdocaoResponseDTO buscarPorId(Long id) {
        Adocao adocao = adocaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Adoção não encontrada com ID: " + id));
        return toResponseDTO(adocao);
    }

    // --- Métodos Auxiliares ---

    private boolean isAnimalAdotado(Animal animal) {
        if (animal.getEventos() == null || animal.getEventos().isEmpty()) {
            return false;
        }
        EventoAnimal ultimoEvento = animal.getEventos().stream()
                .max(Comparator.comparing(EventoAnimal::getDataEvento)
                        .thenComparing(EventoAnimal::getId))
                .orElse(null);
        
        return ultimoEvento instanceof Adocao;
    }

    private AdocaoResponseDTO toResponseDTO(Adocao entity) {
        return AdocaoResponseDTO.builder()
                .id(entity.getId())
                .dataAdocao(entity.getDataEvento())
                .observacoes(entity.getObsEvento())
                .animal(toAnimalDTO(entity.getAnimal()))
                .adotante(toAdotanteDTO(entity.getAdotante()))
                .build();
    }

    private AnimalResponseDTO toAnimalDTO(Animal animal) {
        return AnimalResponseDTO.builder()
                .id(animal.getId())
                .nome(animal.getNome())
                .especieAnimal(animal.getEspecieAnimal())
                .sexo(animal.getSexo())
                .pelagem(animal.getPelagem())
                .bio(animal.getBio())
                .urlFoto(animal.getUrlFoto())
                .dataCriacao(animal.getDataCriacao())
                .adotado(isAnimalAdotado(animal))
                .build();
    }

    private AdotanteResponseDTO toAdotanteDTO(Adotante adotante) {
        EnderecoDTO enderecoDTO = null;
        if (adotante.getEndereco() != null) {
            enderecoDTO = EnderecoDTO.builder()
                    .logradouro(adotante.getEndereco().getLogradouro())
                    .numero(adotante.getEndereco().getNumero())
                    .cidade(adotante.getEndereco().getCidade())
                    .estado(adotante.getEndereco().getEstado())
                    .cep(adotante.getEndereco().getCep())
                    .build();
        }
        return AdotanteResponseDTO.builder()
                .id(adotante.getId())
                .nome(adotante.getNome())
                .documentoIdentificacao(adotante.getDocumentoIdentificacao())
                .endereco(enderecoDTO)
                .build();
    }
}
