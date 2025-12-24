package br.org.miauaumigos.backend.controller;

import br.org.miauaumigos.backend.dto.LoginRequestDTO;
import br.org.miauaumigos.backend.dto.LoginResponseDTO;
import br.org.miauaumigos.backend.security.config.SecurityConfig;
import br.org.miauaumigos.backend.security.jwt.JwtService;
import br.org.miauaumigos.backend.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    // Mocks necessários para o contexto de segurança (SecurityConfig)
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveAutenticarComSucesso() throws Exception {
        // Cenário
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("admin")
                .password("123456")
                .build();

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token("token-jwt-falso")
                .username("admin")
                .nome("Administrador")
                .build();

        when(authService.login(any(LoginRequestDTO.class))).thenReturn(response);

        // Ação & Verificação
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-jwt-falso"))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.tipo").value("Bearer"));
    }

    @Test
    void deveRetornar401QuandoCredenciaisInvalidas() throws Exception {
        // Cenário
        LoginRequestDTO request = LoginRequestDTO.builder()
                .username("admin")
                .password("senhaerrada")
                .build();

        // Simulando o erro que o AuthenticationManager lança
        when(authService.login(any(LoginRequestDTO.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Ação & Verificação
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarErroDeValidacaoQuandoCamposVazios() throws Exception {
        // Cenário: Request vazio
        LoginRequestDTO request = new LoginRequestDTO();

        // Ação & Verificação
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
