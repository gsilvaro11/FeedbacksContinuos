package com.dbc.feedbackscontinuos.dto;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbacksCreateDTO {
    @NotNull
    private Integer idFuncionarioDestino;
    @NotBlank
    private String conteudo;
    private Boolean visivel;
    private List<TagsDTO> listaTags;
}
