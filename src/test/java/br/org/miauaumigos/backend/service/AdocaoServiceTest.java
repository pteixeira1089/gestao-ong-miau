package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.AdocaoRequestDTO;
import br.org.miauaumigos.backend.dto.AdocaoResponseDTO;
import br.org.miauaumigos.backend.model.entity.Adocao;
import br.org.miauaumigos.backend.model.entity.Adotante;
import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.model.entity.Endereco;
import br.org.miauaumigos.backend.repository.AdocaoRepository;
import br.org.miauaumigos.backend.repository.AdotanteRepository;
import br.org.miauaumigos.backend.repository.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private AdotanteRepository adotanteRepository;

    @InjectMocks
    private AdocaoService adocaoService;

    @Test
    void deveRegistrarAdocaoComSucesso() {
        // Cenário
        AdocaoRequestDTO request = AdocaoRequestDTO.builder()
                .animalId(1L)
                .adotanteId(2L)
                .dataAdocao(LocalDate.now())
                .build();

        Animal animal = Animal.builder().id(1L).nome("Rex").build();
        Adotante adotante = Adotante.builder()
                .id(2L)
                .nome("João")
                .endereco(Endereco.builder().logradouro("Rua X").build()) // Necessário para o mapper não quebrar
                .build();
        
        Adocao adocaoSalva = new Adocao();
        adocaoSalva.setId(10L);
        adocaoSalva.setAnimal(animal);
        adocaoSalva.setAdotante(adotante);
        adocaoSalva.setDataEvento(LocalDate.now());

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(adotanteRepository.findById(2L)).thenReturn(Optional.of(adotante));
        when(adocaoRepository.save(any(Adocao.class))).thenReturn(adocaoSalva);

        // Ação
        AdocaoResponseDTO response = adocaoService.registrarAdocao(request);

        // Verificação
        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Rex", response.getAnimal().getNome());
        assertEquals("João", response.getAdotante().getNome());
        
        verify(adocaoRepository).save(any(Adocao.class));
    }

    @Test
    void deveLancarErroQuandoAnimalNaoExiste() {
        AdocaoRequestDTO request = AdocaoRequestDTO.builder().animalId(99L).build();
        
        when(animalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adocaoService.registrarAdocao(request));
        verify(adocaoRepository, never()).save(any());
    }

    @Test
    void deveLancarErroQuandoAdotanteNaoExiste() {
        AdocaoRequestDTO request = AdocaoRequestDTO.builder().animalId(1L).adotanteId(99L).build();
        
        when(animalRepository.findById(1L)).thenReturn(Optional.of(new Animal()));
        when(adotanteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adocaoService.registrarAdocao(request));
        verify(adocaoRepository, never()).save(any());
    }
}
