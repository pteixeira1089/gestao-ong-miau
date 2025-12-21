package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.AnimalRequestDTO;
import br.org.miauaumigos.backend.dto.AnimalResponseDTO;
import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.model.enums.EspecieAnimal;
import br.org.miauaumigos.backend.repository.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    void deveCadastrarAnimalComSucesso() {
        AnimalRequestDTO request = AnimalRequestDTO.builder()
                .nome("Rex")
                .especieAnimal(EspecieAnimal.CACHORRO)
                .sexo('M')
                .pelagem("Curta")
                .build();

        Animal animalSalvo = Animal.builder()
                .id(1L)
                .nome("Rex")
                .especieAnimal(EspecieAnimal.CACHORRO)
                .sexo('M')
                .pelagem("Curta")
                .build();

        when(animalRepository.save(any(Animal.class))).thenReturn(animalSalvo);

        AnimalResponseDTO response = animalService.cadastrar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Rex", response.getNome());
        verify(animalRepository, times(1)).save(any(Animal.class));
    }

    @Test
    void deveListarTodosAnimais() {
        Animal animal = Animal.builder().id(1L).nome("Miau").build();
        when(animalRepository.findAll()).thenReturn(List.of(animal));

        List<AnimalResponseDTO> lista = animalService.listarTodos();

        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        assertEquals("Miau", lista.get(0).getNome());
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        Animal animal = Animal.builder().id(1L).nome("Miau").build();
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        AnimalResponseDTO response = animalService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarIdInexistente() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.buscarPorId(1L));
    }

    @Test
    void deveAtualizarAnimalComSucesso() {
        Animal animalExistente = Animal.builder().id(1L).nome("Rex").build();
        AnimalRequestDTO requestAtualizacao = AnimalRequestDTO.builder()
                .nome("Rex Atualizado")
                .especieAnimal(EspecieAnimal.CACHORRO)
                .sexo('M')
                .pelagem("Longa")
                .build();
        
        Animal animalAtualizado = Animal.builder()
                .id(1L)
                .nome("Rex Atualizado")
                .especieAnimal(EspecieAnimal.CACHORRO)
                .sexo('M')
                .pelagem("Longa")
                .build();

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalExistente));
        when(animalRepository.save(any(Animal.class))).thenReturn(animalAtualizado);

        AnimalResponseDTO response = animalService.atualizar(1L, requestAtualizacao);

        assertEquals("Rex Atualizado", response.getNome());
        assertEquals("Longa", response.getPelagem());
    }

    @Test
    void deveDeletarAnimalComSucesso() {
        when(animalRepository.existsById(1L)).thenReturn(true);
        
        assertDoesNotThrow(() -> animalService.deletar(1L));
        
        verify(animalRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void deveLancarExcecaoAoDeletarIdInexistente() {
        when(animalRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> animalService.deletar(1L));
        
        verify(animalRepository, never()).deleteById(anyLong());
    }
}
