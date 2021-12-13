package com.dbc.feedbackscontinuos.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class FeedbacksDTO {
    private Integer idFeedback;
    private FuncionarioDTO funcionarioOrigem;
    private FuncionarioDTO funcionarioDestino;
    private String conteudo;
    private ZonedDateTime dataFeedback;
    private List<TagsDTO> tags;
}
