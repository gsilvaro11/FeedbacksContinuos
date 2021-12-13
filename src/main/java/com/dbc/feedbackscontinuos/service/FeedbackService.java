package com.dbc.feedbackscontinuos.service;


import com.dbc.feedbackscontinuos.dto.FeedbacksCreateDTO;
import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.dto.TagsDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.entity.TagsEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FeedbackRepository;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.dbc.feedbackscontinuos.repository.TagsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;
    private final FuncionarioService funcionarioService;
    private final TagsService tagsService;

    public FeedbackEntity getById(Integer feedbackId) throws RegraDeNegocioException {
       return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RegraDeNegocioException("Feedback não encontrado."));
    }

    public List<FeedbacksDTO> listarEnviados(Integer idFuncionario) throws RegraDeNegocioException {
        funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado"));

        return feedbackRepository.findByIdFuncionario(idFuncionario).stream()
                .map(x -> {
                    FeedbacksDTO dto = objectMapper.convertValue(x , FeedbacksDTO.class);
                    try {
                        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(idFuncionario));
                        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(x.getIdFuncionarioDestino()));
                        dto.setTags(x.getListaTags().stream().map(tagsEntity -> objectMapper.convertValue(tagsEntity, TagsDTO.class)).collect(Collectors.toList()));
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
                        dto.setTags(x.getListaTags().stream().map(tagsEntity -> objectMapper.convertValue(tagsEntity, TagsDTO.class)).collect(Collectors.toList()));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return dto;
                }).collect(Collectors.toList());

    }

    public List<FeedbacksDTO> listarEnviadosVisivel(Integer idFuncionario) throws RegraDeNegocioException {
        funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado"));

        return feedbackRepository.findByIdVisivelTrue(idFuncionario).stream()
                .map(x -> {
                    FeedbacksDTO dto = objectMapper.convertValue(x , FeedbacksDTO.class);
                    try {
                        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(idFuncionario));
                        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(x.getIdFuncionarioDestino()));
                        dto.setTags(x.getListaTags().stream().map(tagsEntity -> objectMapper.convertValue(tagsEntity, TagsDTO.class)).collect(Collectors.toList()));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return dto;
                }).collect(Collectors.toList());

    }

    public List<FeedbacksDTO> listarRecebidosVisivel(Integer idFuncionario) throws RegraDeNegocioException {
        funcionarioRepository.findById(idFuncionario).orElseThrow(() -> new RegraDeNegocioException("Funcionario não encontrado"));

        return feedbackRepository.findByIdDestinoVisivelTrue(idFuncionario).stream()
                .map(x -> {
                    FeedbacksDTO dto = objectMapper.convertValue(x , FeedbacksDTO.class);
                    try {
                        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(x.getFuncionarioEntity().getIdFuncionario()));
                        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(idFuncionario));
                        dto.setTags(x.getListaTags().stream().map(tagsEntity -> objectMapper.convertValue(tagsEntity, TagsDTO.class)).collect(Collectors.toList()));
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

        Set<TagsEntity> listaTags = feedbacksCreateDTO.getListaTags().stream()
                .map(tagsCreateDTO -> {
                    try {
                        return tagsService.getById(tagsCreateDTO.getIdTag());
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toSet());


        FeedbackEntity entity = objectMapper.convertValue(feedbacksCreateDTO, FeedbackEntity.class);
        entity.setFuncionarioEntity(funcionario);
        entity.setDataFeedback(LocalDateTime.now().minusHours(3));
        entity.setListaTags(listaTags);
        entity.setVisivel(true);
        entity.setStatus(true);
        FeedbackEntity feedbackSalvo = feedbackRepository.save(entity);

        FeedbacksDTO dto = objectMapper.convertValue(feedbackSalvo, FeedbacksDTO.class);
        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(idFuncionario));
        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(feedbacksCreateDTO.getIdFuncionarioDestino()));
        dto.setTags(entity.getListaTags().stream().map(tagsEntity -> objectMapper.convertValue(tagsEntity, TagsDTO.class)).collect(Collectors.toList()));
        return dto;
    }

    public FeedbacksDTO updateVisivel(Integer idFeedback, Integer idFuncionario) throws RegraDeNegocioException {
        FeedbackEntity entity = getById(idFeedback);
        if(idFuncionario == entity.getIdFuncionarioDestino()){
            entity.setVisivel(!entity.getVisivel());
        }else{
            throw new RegraDeNegocioException("Acesso negado!");
        }

        FeedbackEntity feedbackAtualizado = feedbackRepository.save(entity);
        FeedbacksDTO dto = objectMapper.convertValue(feedbackAtualizado, FeedbacksDTO.class);
        dto.setFuncionarioOrigem(funcionarioService.findByIdDTO(entity.getFuncionarioEntity().getIdFuncionario()));
        dto.setFuncionarioDestino(funcionarioService.findByIdDTO(entity.getIdFuncionarioDestino()));
        dto.setTags(entity.getListaTags().stream().map(tagsEntity -> objectMapper.convertValue(tagsEntity, TagsDTO.class)).collect(Collectors.toList()));
        return dto;
    }
}
