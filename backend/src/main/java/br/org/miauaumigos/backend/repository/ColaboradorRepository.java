package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    //Indicate here all the methods that are going to be translated to queries by JPA
    //Important: Use List<T> for queries that can return more than one result
    //Important: For queries that can return no result (like querying for email or cpf), use Optional<T>
    //Important: Use 'Containing', 'StartingWith', 'EndingWiht' and 'IgnoreCase' names to indicate how the query has to be made
    //Important: JPA only implements basic CRUD methods automatically, like getAll and getById. The other queries has to be indicated
    List<Colaborador> findByUsernameContainingIgnoreCase(String username);
    List<Colaborador> findByEmailContainsIgnoreCase(String email);
    List<Colaborador> findByNomeContainsIgnoreCase(String nome);

    Optional<Colaborador> findByUsername(String username);

    // Verifica se username ou email já existem (para evitar duplicatas)
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);




}
