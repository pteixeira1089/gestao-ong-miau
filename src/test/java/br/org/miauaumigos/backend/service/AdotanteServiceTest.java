package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.AdotanteRequestDTO;
import br.org.miauaumigos.backend.dto.AdotanteResponseDTO;
import br.org.miauaumigos.backend.dto.EnderecoDTO;
import br.org.miauaumigos.backend.model.entity.Adotante;
import br.org.miauaumigos.backend.model.entity.Endereco;
import br.org.miauaumigos.backend.repository.AdotanteRepository;
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
class AdotanteServiceTest {

    @Mock
    private AdotanteRepository adotanteRepository;

    @InjectMocks
    private AdotanteService adotanteService;

    @Test
    void deveCadastrarAdotanteComSucesso() {
        // Cenário
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .logradouro("Rua A")
                .numero("10")
                .cidade("Cidade B")
                .estado("SP")
                .cep("00000-000")
                .build();

        AdotanteRequestDTO request = AdotanteRequestDTO.builder()
                .nome("Maria")
                .documentoIdentificacao("123")
                .endereco(enderecoDTO)
                .build();

        Adotante adotanteSalvo = Adotante.builder()
                .id(1L)
                .nome("Maria")
                .documentoIdentificacao("123")
                .endereco(Endereco.builder().logradouro("Rua A").build())
                .build();

        when(adotanteRepository.existsByDocumentoIdentificacao("123")).thenReturn(false);
        when(adotanteRepository.save(any(Adotante.class))).thenReturn(adotanteSalvo);

        // Ação
        AdotanteResponseDTO response = adotanteService.cadastrar(request);

        // Verificação
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Maria", response.getNome());
        assertNotNull(response.getEndereco());
        assertEquals("Rua A", response.getEndereco().getLogradouro());

        verify(adotanteRepository).save(any(Adotante.class));
    }

    @Test
    void deveLancarErroAoCadastrarDocumentoDuplicado() {
        // Cenário
        AdotanteRequestDTO request = AdotanteRequestDTO.builder()
                .documentoIdentificacao("123")
                .build();

        when(adotanteRepository.existsByDocumentoIdentificacao("123")).thenReturn(true);

        // Ação & Verificação
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adotanteService.cadastrar(request);
        });

        assertEquals("Já existe um adotante cadastrado com este documento.", exception.getMessage());
        verify(adotanteRepository, never()).save(any());
    }

    @Test
    void deveListarTodosAdotantes() {
        // Cenário
        Adotante adotante = Adotante.builder().id(1L).nome("João").build();
        when(adotanteRepository.findAll()).thenReturn(List.of(adotante));

        // Ação
        List<AdotanteResponseDTO> lista = adotanteService.listarTodos();

        // Verificação
        assertFalse(lista.isEmpty());
        assertEquals(1, lista.size());
        assertEquals("João", lista.get(0).getNome());
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        // Cenário
        Adotante adotante = Adotante.builder().id(1L).nome("João").build();
        when(adotanteRepository.findById(1L)).thenReturn(Optional.of(adotante));

        // Ação
        AdotanteResponseDTO response = adotanteService.buscarPorId(1L);

        // Verificação
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void deveLancarErroAoBuscarIdInexistente() {
        // Cenário
        when(adotanteRepository.findById(1L)).thenReturn(Optional.empty());

        // Ação & Verificação
        assertThrows(EntityNotFoundException.class, () -> {
            adotanteService.buscarPorId(1L);
        });
    }
}
