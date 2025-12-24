package br.org.miauaumigos.backend.service;

import br.org.miauaumigos.backend.model.entity.Role;
import br.org.miauaumigos.backend.model.enums.RoleEnum;
import br.org.miauaumigos.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role create(RoleEnum roleEnum) {
        //Verifica se a role já existe
        if (roleRepository.findByRole(roleEnum).isPresent()) {
            throw new IllegalArgumentException("Role já existente: " + roleEnum);
        }

        Role role = Role.of(roleEnum);
        return roleRepository.save(role);
    }

    public Role findByRoleName(RoleEnum role) {
        return roleRepository.findByRole(role)
                .orElseThrow(() -> new NoSuchElementException("Role não encontrada: " + role));
    }

    public Role findByRoleId(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Não encontrada role de ID n. " + id));
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role update(Long id, RoleEnum newRoleEnum) {
        Role existingRole = findByRoleId(id);

        //Verifica se a nova role já existe (para evitar duplicação)
        if (roleRepository.findByRole(newRoleEnum).isPresent()) {
            throw new IllegalArgumentException("Role já existente: " + newRoleEnum);
        }

        existingRole.setRole(newRoleEnum);
        return roleRepository.save(existingRole);
    }

    public void delete(Long id){
        if(!roleRepository.findById(id).isPresent()){
            throw new NoSuchElementException("role de ID n. " + id + " não encontrada,.");
        }

        roleRepository.deleteById(id);
    }

    // Método auxiliar para garantir que roles padrão existam no sistema
    public void initializeDefaultRoles() {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleRepository.findByRole(roleEnum).isEmpty()) {
                roleRepository.save(Role.of(roleEnum));
            }
        }
    }
}
