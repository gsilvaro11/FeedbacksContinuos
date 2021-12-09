package com.dbc.feedbackscontinuos.dto;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
public class FeedbacksCreateDTO {
    private FuncionarioEntity idFuncionario;
    private FuncionarioEntity idFuncionarioDestino;
    private String conteudo;
    private Boolean deletado;
    private Boolean visivel;
}
