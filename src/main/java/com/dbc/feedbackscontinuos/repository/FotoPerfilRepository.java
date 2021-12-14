package com.dbc.feedbackscontinuos.repository;

import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoPerfilRepository extends JpaRepository<FotoPerfilEntity, Integer> {

    @Query(value = "select * from foto_perfil fp where fp.id_funcionario = :idFuncionario", nativeQuery = true)
    FotoPerfilEntity buscarFotoPorIdFuncionario(Integer idFuncionario);
}
