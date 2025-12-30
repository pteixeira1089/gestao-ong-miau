package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.EventoPadrao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoPadraoRepository extends JpaRepository<EventoPadrao, Long> {
    List<EventoPadrao> findByAnimalIdOrderByDataEventoDesc(Long animalId);
}
