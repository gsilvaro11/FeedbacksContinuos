package com.dbc.feedbackscontinuos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbacksCreateDTO {
    @NotNull
    @ApiModelProperty(value = "Id do usuário destino que receberá o feedback")
    private Integer idFuncionarioDestino;
    @NotBlank
    @NotNull
    @Size(max = 500)
    @ApiModelProperty(value = "Texto do feedback.")
    private String conteudo;
    @NotNull
    @ApiModelProperty(value = "True para feedback público e false para feedback privado.")
    private Boolean visivel;
    @ApiModelProperty(value = "Lista de tags que serão incluídas no feedback.")
    private List<TagsCreateDTO> listaTags;
    @NotNull
    private Boolean anonimo;

}
