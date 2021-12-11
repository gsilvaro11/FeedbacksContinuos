package com.dbc.feedbackscontinuos.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class FeedbacksDTO {
    private Integer idFeedback;
    private FuncionarioDTO funcionarioOrigem;
    private FuncionarioDTO funcionarioDestino;
    private String conteudo;
    private LocalDateTime dataFeedback;
    private List<TagsDTO> tags;
}
