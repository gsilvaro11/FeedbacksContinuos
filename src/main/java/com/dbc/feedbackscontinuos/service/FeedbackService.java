package com.dbc.feedbackscontinuos.service;


import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;

import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FeedbackRepository;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    public List<FeedbacksDTO> list(Integer idFuncionario) throws RegraDeNegocioException {
        funcionarioRepository.findById(idFuncionario).orElseThrow(
                () -> new RegraDeNegocioException("Funcionario não encontrado"));

        List<FeedbacksDTO> feedbacksDTOList = new ArrayList<>();
        List<FeedbackEntity> feedbackEntityList = feedbackRepository.findByIdFuncionario(idFuncionario);

        for(FeedbackEntity feedbackEntity : feedbackEntityList) {
            FeedbacksDTO feedbacksDTO = objectMapper.convertValue(feedbackEntity, FeedbacksDTO.class);
            feedbacksDTOList.add(feedbacksDTO);
        }
        return feedbacksDTOList;


    }

}
