package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.*;
import br.org.miauaumigos.backend.model.entity.Adocao;
import br.org.miauaumigos.backend.model.entity.Adotante;
import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.repository.AdocaoRepository;
import br.org.miauaumigos.backend.repository.AdotanteRepository;
import br.org.miauaumigos.backend.repository.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Adotante adotante = adotanteRepository.findById(dto.getAdotanteId())
                .orElseThrow(() -> new EntityNotFoundException("Adotante não encontrado com ID: " + dto.getAdotanteId()));

        // Aqui poderíamos adicionar validações extras, ex: se o animal já está adotado.

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

    // --- Mappers Auxiliares ---

    private AdocaoResponseDTO toResponseDTO(Adocao entity) {
        return AdocaoResponseDTO.builder()
                .id(entity.getId())
                .dataAdocao(entity.getDataEvento())
                .observacoes(entity.getObsEvento())
                .animal(toAnimalDTO(entity.getAnimal()))
                .adotante(toAdotanteDTO(entity.getAdotante()))
                .build();
    }

    // Duplicando mappers simples para evitar acoplamento circular com outros Services
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
