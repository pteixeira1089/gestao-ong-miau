package br.org.miauaumigos.backend.repository;

import br.org.miauaumigos.backend.model.entity.VinculoFuncional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VinculoFuncionalRepository extends JpaRepository<VinculoFuncional, Long> {
    List<VinculoFuncional> findByColaboradorId(Long colaboradorId);

    List<VinculoFuncional> findByColaboradorUsername(String username);

    List<VinculoFuncional> findByColaboradorEmail(String email);

    List<VinculoFuncional> findByColaboradorNome(String nome);

}
