package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.repository.ColaboradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColaboradorService {

    private final ColaboradorRepository colaboradorRepository;
    private final PasswordEncoder passwordEncoder;
}
