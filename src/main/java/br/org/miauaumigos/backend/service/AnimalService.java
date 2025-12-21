package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.AnimalRequestDTO;
import br.org.miauaumigos.backend.dto.AnimalResponseDTO;
import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.repository.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Transactional
    public AnimalResponseDTO cadastrar(AnimalRequestDTO dto) {
        Animal animal = toEntity(dto);
        Animal animalSalvo = animalRepository.save(animal);
        return toResponseDTO(animalSalvo);
    }

    @Transactional(readOnly = true)
    public List<AnimalResponseDTO> listarTodos() {
        return animalRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AnimalResponseDTO buscarPorId(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com o ID: " + id));
        return toResponseDTO(animal);
    }

    @Transactional
    public AnimalResponseDTO atualizar(Long id, AnimalRequestDTO dto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal não encontrado com o ID: " + id));

        animal.setNome(dto.getNome());
        animal.setEspecieAnimal(dto.getEspecieAnimal());
        animal.setSexo(dto.getSexo());
        animal.setPelagem(dto.getPelagem());
        animal.setBio(dto.getBio());
        animal.setUrlFoto(dto.getUrlFoto());

        Animal animalAtualizado = animalRepository.save(animal);
        return toResponseDTO(animalAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new EntityNotFoundException("Animal não encontrado com o ID: " + id);
        }
        animalRepository.deleteById(id);
    }

    // --- Métodos Auxiliares de Conversão (Mappers) ---

    private Animal toEntity(AnimalRequestDTO dto) {
        return Animal.builder()
                .nome(dto.getNome())
                .especieAnimal(dto.getEspecieAnimal())
                .sexo(dto.getSexo())
                .pelagem(dto.getPelagem())
                .bio(dto.getBio())
                .urlFoto(dto.getUrlFoto())
                .build();
    }

    private AnimalResponseDTO toResponseDTO(Animal animal) {
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
}
