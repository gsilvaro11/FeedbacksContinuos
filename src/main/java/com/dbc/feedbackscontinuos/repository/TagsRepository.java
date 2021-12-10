package com.dbc.feedbackscontinuos.repository;

import com.dbc.feedbackscontinuos.entity.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<TagsEntity, Integer> {
}
