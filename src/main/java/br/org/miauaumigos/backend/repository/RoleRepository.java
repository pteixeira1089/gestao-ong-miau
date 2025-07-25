package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.Role;
import br.org.miauaumigos.backend.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RoleEnum role);
}
