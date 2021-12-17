package com.dbc.feedbackscontinuos.repository;

import com.dbc.feedbackscontinuos.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {
    @Query(value = "SELECT * FROM FEEDBACK f where f.id_funcionario = :idFuncionario order by f.data_feedback desc", nativeQuery = true)
    List<FeedbackEntity> findByIdFuncionario(Integer idFuncionario);

    @Query(value = "SELECT * FROM FEEDBACK f where f.id_funcionario_destino = :idFuncionarioDestino order by f.data_feedback desc",
            nativeQuery = true)
    List<FeedbackEntity> findByIdFuncionarioDestino(Integer idFuncionarioDestino);
}
