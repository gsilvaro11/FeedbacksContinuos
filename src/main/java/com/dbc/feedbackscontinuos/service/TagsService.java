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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagsService {
    private final TagsRepository repository;
    private final ObjectMapper objectMapper;
    private final FeedbackRepository feedbackRepository;

    public List<TagsDTO> list() {
        List<TagsEntity> listaEntity = repository.findByStatus();
        return listaEntity.stream()
                .map(x -> objectMapper.convertValue(x, TagsDTO.class))
                .collect(Collectors.toList());

    }


    public TagsEntity findById(Integer id) throws RegraDeNegocioException {
        return repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Tag não encontrada."));
    }
}
