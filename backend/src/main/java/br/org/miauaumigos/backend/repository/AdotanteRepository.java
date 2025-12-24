package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.Adotante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdotanteRepository extends JpaRepository<Adotante, Long> {
    
    boolean existsByDocumentoIdentificacao(String documentoIdentificacao);
    
    Optional<Adotante> findByDocumentoIdentificacao(String documentoIdentificacao);
}
