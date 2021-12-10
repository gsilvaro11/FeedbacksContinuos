package com.dbc.feedbackscontinuos.dto;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import lombok.Data;

@Data
public class FeedbacksDTO {
    private Integer idFeedback;
    private FuncionarioDTO funcionario;
    private FuncionarioDTO funcionarioDestino;
    private String conteudo;
}
