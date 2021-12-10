package com.dbc.feedbackscontinuos.service;


import com.dbc.feedbackscontinuos.dto.FeedbacksCreateDTO;
import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;

import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FeedbackRepository;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;
    private final FuncionarioService funcionarioService;

    public List<FeedbacksDTO> list(Integer idFuncionario) throws RegraDeNegocioException {
        funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado"));



        return feedbackRepository.findByIdFuncionario(idFuncionario).stream()
                .map(x -> {
                    FeedbacksDTO dto = objectMapper.convertValue(x , FeedbacksDTO.class);
                    try {
                        dto.setFuncionario(funcionarioService.findByIdDTO(idFuncionario));
                        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(x.getIdFuncionarioDestino()));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return dto;
                }).collect(Collectors.toList());

    }

    public FeedbacksDTO create(FeedbacksCreateDTO feedbacksCreateDTO) throws RegraDeNegocioException {
       funcionarioRepository.findById(feedbacksCreateDTO.getFuncionario())
               .orElseThrow(() -> new RegraDeNegocioException(("Funcionário de origem não encontrado!")));

        funcionarioRepository.findById(feedbacksCreateDTO.getFuncionario())
                .orElseThrow(() -> new RegraDeNegocioException(("Destino não encontrado!")));

        FeedbackEntity entity = objectMapper.convertValue(feedbacksCreateDTO, FeedbackEntity.class);
        FeedbackEntity feedbackSalvo = feedbackRepository.save(entity);

        return objectMapper.convertValue(feedbackSalvo, FeedbacksDTO.class);
    }
}
