package com.dbc.feedbackscontinuos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FuncionarioComFeedbacksDTO {
    private Integer idFuncionario;
    private String nome;
    private String email;
    private String fotoFuncionario;
    private List<FeedbacksOrigemDTO> recebidos;
}