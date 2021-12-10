package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;
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

    public List<FeedbacksDTO> list(Integer idFuncionario){
        List<FeedbacksDTO> feedbacksDTOS = new ArrayList<>();
        

        return feedbackRepository.findByIdFuncionario(idFuncionario);
    }

}
