package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.dto.TagsDTO;
import com.dbc.feedbackscontinuos.entity.TagsEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FeedbackRepository;
import com.dbc.feedbackscontinuos.repository.TagsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagsService {
    private final TagsRepository repository;
    private final ObjectMapper objectMapper;
    private final FeedbackRepository feedbackRepository;

    public TagsDTO create(TagsDTO tagsDTO) throws RegraDeNegocioException {
        TagsEntity entity = objectMapper.convertValue(tagsDTO, TagsEntity.class);
        Optional<TagsEntity> tags = repository.findByNomeTag(tagsDTO.getNomeTag());
        if(tags.isPresent()){
            throw new RegraDeNegocioException("Tag já cadastrada.");
        }

        entity.setStatus(true);
        repository.save(entity);

        return objectMapper.convertValue(entity, TagsDTO.class);
    }
}
