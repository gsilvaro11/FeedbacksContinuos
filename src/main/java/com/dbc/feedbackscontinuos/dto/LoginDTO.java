package com.dbc.feedbackscontinuos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginDTO {
    @ApiModelProperty(value = "E-mail cadastrado do usuário.")
    private String email;
    @ApiModelProperty(value = "Senha cadastrada do usuário.")
    private String senha;
}
