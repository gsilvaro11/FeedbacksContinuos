package com.dbc.feedbackscontinuos.dto;

import lombok.Data;

import java.util.List;

@Data
public class FeedbacksDTO {
    private Integer idFeedback;
    private FuncionarioDTO funcionarioOrigem;
    private FuncionarioDTO funcionarioDestino;
    private String conteudo;
    private List<TagsDTO> tags;
}
