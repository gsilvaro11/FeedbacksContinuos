package com.dbc.feedbackscontinuos.repository;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer> {
    Optional<FuncionarioEntity> findByEmail(String email);

    @Query(value = "select * from Funcionario f where f.id_funcionario != :id order by f.nome", nativeQuery = true)
    List<FuncionarioEntity> findFuncionariosExeto(Integer id);
}
