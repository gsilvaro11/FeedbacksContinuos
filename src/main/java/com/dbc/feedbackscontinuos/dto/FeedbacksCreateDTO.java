package com.dbc.feedbackscontinuos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
