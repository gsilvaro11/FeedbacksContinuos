package com.dbc.feedbackscontinuos.dto;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
public class FeedbacksCreateDTO {
    private Integer funcionario;
    private Integer funcionarioDestino;
    private String conteudo;
    private Boolean visivel;
}
