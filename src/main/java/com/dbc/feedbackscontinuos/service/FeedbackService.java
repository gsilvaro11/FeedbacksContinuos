package com.dbc.feedbackscontinuos.service;


import com.dbc.feedbackscontinuos.dto.FeedbacksCreateDTO;
import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
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

    public List<FeedbacksDTO> listarEnviados(Integer idFuncionario) throws RegraDeNegocioException {
        funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado"));



        return feedbackRepository.findByIdFuncionario(idFuncionario).stream()
                .map(x -> {
                    FeedbacksDTO dto = objectMapper.convertValue(x , FeedbacksDTO.class);
                    try {
                        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(idFuncionario));
                        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(x.getIdFuncionarioDestino()));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return dto;
                }).collect(Collectors.toList());

    }

    public List<FeedbacksDTO> listarRecebidos(Integer idFuncionario) throws RegraDeNegocioException {
        funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado"));



        return feedbackRepository.findByIdFuncionarioDestino(idFuncionario).stream()
                .map(x -> {
                    FeedbacksDTO dto = objectMapper.convertValue(x , FeedbacksDTO.class);
                    try {
                        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(x.getFuncionarioEntity().getIdFuncionario()));
                        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(idFuncionario));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return dto;
                }).collect(Collectors.toList());

    }

    public FeedbacksDTO create(FeedbacksCreateDTO feedbacksCreateDTO, Integer idFuncionario) throws RegraDeNegocioException {
       FuncionarioEntity funcionario = funcionarioRepository.findById(idFuncionario)
               .orElseThrow(() -> new RegraDeNegocioException(("Funcionário de origem não encontrado!")));

        funcionarioRepository.findById(feedbacksCreateDTO.getIdFuncionarioDestino())
                .orElseThrow(() -> new RegraDeNegocioException(("Destino não encontrado!")));


        FeedbackEntity entity = objectMapper.convertValue(feedbacksCreateDTO, FeedbackEntity.class);
        entity.setFuncionarioEntity(funcionario);
        FeedbackEntity feedbackSalvo = feedbackRepository.save(entity);

        FeedbacksDTO dto = objectMapper.convertValue(feedbackSalvo, FeedbacksDTO.class);
        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(idFuncionario));
        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(feedbacksCreateDTO.getIdFuncionarioDestino()));
        return dto;
    }
}
