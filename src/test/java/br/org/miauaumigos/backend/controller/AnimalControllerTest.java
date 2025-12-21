package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.AnimalRequestDTO;
import br.org.miauaumigos.backend.dto.AnimalResponseDTO;
import br.org.miauaumigos.backend.model.enums.EspecieAnimal;
import br.org.miauaumigos.backend.service.AnimalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnimalController.class)
class AnimalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCadastrarAnimalComSucesso() throws Exception {
        AnimalRequestDTO request = AnimalRequestDTO.builder()
                .nome("Rex")
                .especieAnimal(EspecieAnimal.CACHORRO)
                .sexo('M')
                .pelagem("Curta")
                .build();

        AnimalResponseDTO response = AnimalResponseDTO.builder()
                .id(1L)
                .nome("Rex")
                .especieAnimal(EspecieAnimal.CACHORRO)
                .sexo('M')
                .pelagem("Curta")
                .build();

        when(animalService.cadastrar(any(AnimalRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/animais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Rex"));
    }

    @Test
    void deveRetornarErroDeValidacaoAoCadastrarAnimalInvalido() throws Exception {
        AnimalRequestDTO request = new AnimalRequestDTO(); // Campos obrigatórios nulos

        mockMvc.perform(post("/api/animais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveListarTodosAnimais() throws Exception {
        AnimalResponseDTO animal = AnimalResponseDTO.builder().id(1L).nome("Miau").build();
        when(animalService.listarTodos()).thenReturn(List.of(animal));

        mockMvc.perform(get("/api/animais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Miau"));
    }

    @Test
    void deveBuscarPorIdComSucesso() throws Exception {
        AnimalResponseDTO animal = AnimalResponseDTO.builder().id(1L).nome("Miau").build();
        when(animalService.buscarPorId(1L)).thenReturn(animal);

        mockMvc.perform(get("/api/animais/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Miau"));
    }

    @Test
    void deveRetornar404AoBuscarIdInexistente() throws Exception {
        when(animalService.buscarPorId(1L)).thenThrow(new EntityNotFoundException("Animal não encontrado"));

        mockMvc.perform(get("/api/animais/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"));
    }

    @Test
    void deveAtualizarAnimalComSucesso() throws Exception {
        AnimalRequestDTO request = AnimalRequestDTO.builder()
                .nome("Rex Atualizado")
                .especieAnimal(EspecieAnimal.CACHORRO)
                .sexo('M')
                .pelagem("Longa")
                .build();

        AnimalResponseDTO response = AnimalResponseDTO.builder()
                .id(1L)
                .nome("Rex Atualizado")
                .build();

        when(animalService.atualizar(eq(1L), any(AnimalRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/animais/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Rex Atualizado"));
    }

    @Test
    void deveDeletarAnimalComSucesso() throws Exception {
        mockMvc.perform(delete("/api/animais/1"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deveRetornar404AoDeletarIdInexistente() throws Exception {
        doThrow(new EntityNotFoundException("Animal não encontrado")).when(animalService).deletar(1L);

        mockMvc.perform(delete("/api/animais/1"))
                .andExpect(status().isNotFound());
    }
}
