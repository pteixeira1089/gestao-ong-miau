package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.Adocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {
}
