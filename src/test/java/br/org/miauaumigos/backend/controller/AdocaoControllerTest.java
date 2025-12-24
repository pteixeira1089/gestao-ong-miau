package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.AdocaoRequestDTO;
import br.org.miauaumigos.backend.dto.AdocaoResponseDTO;
import br.org.miauaumigos.backend.dto.AdotanteResponseDTO;
import br.org.miauaumigos.backend.dto.AnimalResponseDTO;
import br.org.miauaumigos.backend.security.config.SecurityConfig;
import br.org.miauaumigos.backend.security.jwt.JwtService;
import br.org.miauaumigos.backend.service.AdocaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdocaoController.class)
@Import(SecurityConfig.class)
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdocaoService adocaoService;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin")
    void deveRegistrarAdocaoComSucesso() throws Exception {
        AdocaoRequestDTO request = AdocaoRequestDTO.builder()
                .animalId(1L)
                .adotanteId(2L)
                .dataAdocao(LocalDate.now())
                .observacoes("Adoção feliz")
                .build();

        AdocaoResponseDTO response = AdocaoResponseDTO.builder()
                .id(10L)
                .dataAdocao(LocalDate.now())
                .animal(AnimalResponseDTO.builder().id(1L).nome("Rex").build())
                .adotante(AdotanteResponseDTO.builder().id(2L).nome("João").build())
                .build();

        when(adocaoService.registrarAdocao(any(AdocaoRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/adocoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.animal.nome").value("Rex"))
                .andExpect(jsonPath("$.adotante.nome").value("João"));
    }
}
