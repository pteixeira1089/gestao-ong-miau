package br.org.miauaumigos.backend.config;

import br.org.miauaumigos.backend.model.entity.Colaborador;
import br.org.miauaumigos.backend.model.entity.Role;
import br.org.miauaumigos.backend.model.enums.RoleEnum;
import br.org.miauaumigos.backend.repository.ColaboradorRepository;
import br.org.miauaumigos.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ColaboradorRepository colaboradorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.admin.username}")
    private String adminUsername;

    @Value("${security.admin.password}")
    private String adminPassword;

    @Value("${security.admin.email}")
    private String adminEmail;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Iniciando verificação de dados iniciais...");

        // 1. Garantir que a Role ADMIN existe
        Role adminRole = createRoleIfNotFound(RoleEnum.ROLE_ADMIN);
        
        // Garantir que a Role USER existe (opcional, mas boa prática)
        createRoleIfNotFound(RoleEnum.ROLE_USER);

        // 2. Garantir que o Usuário Admin existe
        if (colaboradorRepository.findByUsername(adminUsername).isEmpty()) {
            log.info("Usuário admin não encontrado. Criando usuário '{}'...", adminUsername);
            
            Colaborador admin = Colaborador.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword)) // IMPORTANTE: Hash da senha
                    .nome("Administrador do Sistema")
                    .email(adminEmail)
                    .roles(Set.of(adminRole))
                    .build();

            colaboradorRepository.save(admin);
            log.info("Usuário admin criado com sucesso.");
        } else {
            log.info("Usuário admin já existe. Nenhuma ação necessária.");
        }
    }

    private Role createRoleIfNotFound(RoleEnum roleEnum) {
        Optional<Role> roleOpt = roleRepository.findByRole(roleEnum);
        if (roleOpt.isEmpty()) {
            log.info("Role '{}' não encontrada. Criando...", roleEnum);
            Role novaRole = Role.builder().role(roleEnum).build();
            return roleRepository.save(novaRole);
        }
        return roleOpt.get();
    }
}
