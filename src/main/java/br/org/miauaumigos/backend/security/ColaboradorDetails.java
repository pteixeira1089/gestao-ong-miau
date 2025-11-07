package br.org.miauaumigos.backend.security;

import br.org.miauaumigos.backend.model.entity.Colaborador;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class ColaboradorDetails implements UserDetails {

    private final Colaborador colaborador;

    public ColaboradorDetails(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return colaborador.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return colaborador.getPassword();
    }

    @Override
    public String getUsername() {
        return colaborador.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ou você pode adicionar um campo 'accountNonExpired' na entidade Colaborador e mapeá-lo aqui
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ou você pode adicionar um campo 'accountNonLocked' na entidade Colaborador e mapeá-lo aqui
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ou você pode adicionar um campo 'credentialsNonExpired' na entidade Colaborador e mapeá-lo aqui
    }

    @Override
    public boolean isEnabled() {
        return true; // Ou você pode adicionar um campo 'enabled' na entidade Colaborador e mapeá-lo aqui
    }

    public Colaborador getColaborador() {
        return colaborador;
    }
}
