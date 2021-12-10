package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.dto.TagsCreateDTO;
import com.dbc.feedbackscontinuos.dto.TagsDTO;
import com.dbc.feedbackscontinuos.entity.TagsEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FeedbackRepository;
import com.dbc.feedbackscontinuos.repository.TagsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsService {
    private final TagsRepository repository;
    private final ObjectMapper objectMapper;
    private final FeedbackRepository feedbackRepository;

    public List<TagsDTO> create(TagsCreateDTO tagsCreateDTO) throws RegraDeNegocioException {
        return null;
    }
}
