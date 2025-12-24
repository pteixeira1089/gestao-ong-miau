package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.dto.LoginRequestDTO;
import br.org.miauaumigos.backend.dto.LoginResponseDTO;
import br.org.miauaumigos.backend.model.entity.Colaborador;
import br.org.miauaumigos.backend.repository.ColaboradorRepository;
import br.org.miauaumigos.backend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ColaboradorRepository colaboradorRepository;

    public LoginResponseDTO login(LoginRequestDTO request) {
        // 1. Autenticar (lança exceção se falhar)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Se chegou aqui, está autenticado. Gerar Token.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        // 3. Buscar dados extras do colaborador para retornar
        Colaborador colaborador = colaboradorRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // 4. Montar resposta
        return LoginResponseDTO.builder()
                .token(token)
                .username(colaborador.getUsername())
                .nome(colaborador.getNome())
                .build();
    }
}
