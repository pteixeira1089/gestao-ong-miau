package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.Animal;
import br.org.miauaumigos.backend.model.entity.Colaborador;
import br.org.miauaumigos.backend.model.enums.EspecieAnimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
    //Indicate here all the methods that are going to be translated to queries by JPA
    //Important: Use List<T> for queries that can return more than one result
    //Important: For queries that can return no result (like querying for email or cpf), use Optional<T>
    //Important: Use 'Containing', 'StartingWith', 'EndingWiht' and 'IgnoreCase' names to indicate how the query has to be made
    //Important: JPA only implements basic CRUD methods automatically, like getAll and getById. The other queries has to be indicated
    List<Animal> findByNomeContainsIgnoreCase(String nome);
    List<Animal> findByEspecieAnimal(EspecieAnimal especieAnimal);
    List<Animal> findByEspecieAnimalAndNomeContainingIgnoreCase(EspecieAnimal especieAnimal, String nome);
    List<Animal> findBySexoAndNomeContainingIgnoreCase(Character sexo, String nome);
    List<Animal> findBySexoAndEspecieAnimal(Character sexo, EspecieAnimal especieAnimal);
    List<Animal> findBySexoAndEspecieAnimalAndNomeContainingIgnoreCase(Character sexo, EspecieAnimal especieAnimal, String nome);

}
