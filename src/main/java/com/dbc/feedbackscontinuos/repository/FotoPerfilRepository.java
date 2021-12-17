package com.dbc.feedbackscontinuos.repository;

import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FotoPerfilRepository extends JpaRepository<FotoPerfilEntity, Integer> {

    @Query(value = "select count(fp.id_funcionario) from foto_perfil fp where fp.id_funcionario = :idFuncionario", nativeQuery = true)
    Integer verificaSeFuncionarioPossuiFoto(Integer idFuncionario);

    @Query(value = "select * from foto_perfil fp where fp.id_funcionario = :idFuncionario", nativeQuery = true)
    Optional<FotoPerfilEntity> buscarFotoPorIdFuncionario(Integer idFuncionario);
}
