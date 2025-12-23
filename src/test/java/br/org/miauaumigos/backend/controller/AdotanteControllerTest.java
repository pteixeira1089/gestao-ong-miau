package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.AdotanteRequestDTO;
import br.org.miauaumigos.backend.dto.AdotanteResponseDTO;
import br.org.miauaumigos.backend.dto.EnderecoDTO;
import br.org.miauaumigos.backend.security.config.SecurityConfig;
import br.org.miauaumigos.backend.security.jwt.JwtService;
import br.org.miauaumigos.backend.service.AdotanteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdotanteController.class)
@Import(SecurityConfig.class)
class AdotanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdotanteService adotanteService;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    // --- Testes de Segurança ---

    @Test
    void deveRetornar403QuandoNaoAutenticado() throws Exception {
        mockMvc.perform(get("/api/adotantes"))
                .andExpect(status().isForbidden());
    }

    // --- Testes Funcionais ---

    @Test
    @WithMockUser(username = "admin")
    void deveCadastrarAdotanteComSucesso() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoDTO.builder()
                .logradouro("Rua Teste")
                .numero("123")
                .cidade("Cidade Teste")
                .estado("SP")
                .cep("12345-678")
                .build();

        AdotanteRequestDTO request = AdotanteRequestDTO.builder()
                .nome("João Silva")
                .documentoIdentificacao("123.456.789-00")
                .endereco(enderecoDTO)
                .build();

        AdotanteResponseDTO response = AdotanteResponseDTO.builder()
                .id(1L)
                .nome("João Silva")
                .endereco(enderecoDTO)
                .build();

        when(adotanteService.cadastrar(any(AdotanteRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/adotantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.endereco.logradouro").value("Rua Teste"));
    }

    @Test
    @WithMockUser(username = "admin")
    void deveRetornarErroDeValidacaoQuandoEnderecoInvalido() throws Exception {
        // Endereço sem campos obrigatórios
        EnderecoDTO enderecoInvalido = EnderecoDTO.builder().build();

        AdotanteRequestDTO request = AdotanteRequestDTO.builder()
                .nome("João Silva")
                .documentoIdentificacao("123.456.789-00")
                .endereco(enderecoInvalido)
                .build();

        mockMvc.perform(post("/api/adotantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin")
    void deveListarTodosAdotantes() throws Exception {
        AdotanteResponseDTO adotante = AdotanteResponseDTO.builder().id(1L).nome("João").build();
        when(adotanteService.listarTodos()).thenReturn(List.of(adotante));

        mockMvc.perform(get("/api/adotantes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    @WithMockUser(username = "admin")
    void deveBuscarPorIdComSucesso() throws Exception {
        AdotanteResponseDTO adotante = AdotanteResponseDTO.builder().id(1L).nome("João").build();
        when(adotanteService.buscarPorId(1L)).thenReturn(adotante);

        mockMvc.perform(get("/api/adotantes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @WithMockUser(username = "admin")
    void deveRetornar404AoBuscarIdInexistente() throws Exception {
        when(adotanteService.buscarPorId(1L)).thenThrow(new EntityNotFoundException("Adotante não encontrado"));

        mockMvc.perform(get("/api/adotantes/1"))
                .andExpect(status().isNotFound());
    }
}
