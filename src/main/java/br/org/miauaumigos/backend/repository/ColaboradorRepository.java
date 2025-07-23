package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    //Essas implementações explícitas são opcionais, pois o Spring Data JPA já fornece métodos semelhantes.
    //Estão aqui apenas para clareza, fins didáticos e personalização futura, se necessário.
    //A busca pelos outros atributos de Colaborador pode ser feita diretamente pelo Spring Data JPA.
    Optional<Colaborador> findByUsername(String username);
    Optional<Colaborador> findByEmail(String email);

    // Verifica se username ou email já existem (para evitar duplicatas)
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);




}
