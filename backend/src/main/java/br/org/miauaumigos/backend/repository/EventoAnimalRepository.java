package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.EventoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoAnimalRepository extends JpaRepository<EventoAnimal, Long> {
    List<EventoAnimal> findByAnimalIdOrderByDataEventoDesc(Long animalId);
}
