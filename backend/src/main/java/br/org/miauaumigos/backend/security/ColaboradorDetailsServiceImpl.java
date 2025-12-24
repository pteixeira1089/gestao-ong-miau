package br.org.miauaumigos.backend.security;

import br.org.miauaumigos.backend.model.entity.Colaborador;
import br.org.miauaumigos.backend.repository.ColaboradorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorDetailsServiceImpl implements UserDetailsService {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorDetailsServiceImpl(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Colaborador colaborador = colaboradorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        return new ColaboradorDetails(colaborador);
    }
}
