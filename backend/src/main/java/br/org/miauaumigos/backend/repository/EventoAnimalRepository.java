package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.model.entity.EventoAnimal;
import br.org.miauaumigos.backend.model.enums.TipoEventoAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventoAnimalRepository extends JpaRepository<EventoAnimal, Long> {
    //Indicate here all the methods that are going to be translated to queries by JPA
    //Important: Use List<T> for queries that can return more than one result
    //Important: For queries that can return no result (like querying for email or cpf), use Optional<T>
    //Important: Use 'Containing', 'StartingWith', 'EndingWiht' and 'IgnoreCase' names to indicate how the query has to be made
    //Important: JPA only implements basic CRUD methods automatically, like getAll and getById. The other queries has to be indicated
    List<EventoAnimal> findEventoAnimalByAnimal(Animal animal);

    List<EventoAnimal> findEventoAnimalByDataEvento(LocalDate dataEvento);
}
