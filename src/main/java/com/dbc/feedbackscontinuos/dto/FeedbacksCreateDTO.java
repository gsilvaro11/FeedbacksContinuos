package com.dbc.feedbackscontinuos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class FeedbacksCreateDTO {
    @NotNull
    @ApiModelProperty(value = "Id do usuário destino que receberá o feedback")
    private Integer idFuncionarioDestino;
    @NotBlank
    @NotNull
    @Size(max = 500)
    @ApiModelProperty(value = "Texto do feedback.")
    private String conteudo;
    @ApiModelProperty(value = "Lista de tags que serão incluídas no feedback.")
    private List<TagsCreateDTO> listaTags;
    @NotNull
    @ApiModelProperty(value = "True para feedback anônimo e false para não-anônimo")
    private Boolean anonimo;
}
