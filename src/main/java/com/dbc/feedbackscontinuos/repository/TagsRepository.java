package com.dbc.feedbackscontinuos.repository;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.entity.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, Integer> {
    Optional<TagsEntity> findByNomeTag(String nome);

    @Query(value = "select * from tags t where t.status = true", nativeQuery = true)
    List<TagsEntity> findByStatus();

}
