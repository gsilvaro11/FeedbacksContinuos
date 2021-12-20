package com.dbc.feedbackscontinuos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class FeedbacksOrigemDTO {
    private Integer idFeedback;
    private FuncionarioDTO funcionarioOrigem;
        private String conteudo;
    private LocalDateTime dataFeedback;
    private List<TagsDTO> tags;
    private Boolean anonimo;
    private Boolean visivel;
}
