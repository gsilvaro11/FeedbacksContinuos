package com.dbc.feedbackscontinuos.dto;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import lombok.Data;

@Data
public class FeedbacksDTO {
    private Integer idFeedback;
    private FuncionarioDTO idFuncionario;
    private FuncionarioDTO idFuncionarioDestino;
    private String conteudo;
}
