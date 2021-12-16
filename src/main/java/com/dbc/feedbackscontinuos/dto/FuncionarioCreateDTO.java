package com.dbc.feedbackscontinuos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioCreateDTO {
    @NotBlank
    @NotNull
    @ApiModelProperty(value = "Nome do usuário.")
    private String nome;

    @NotBlank
    @Email
    @NotNull
    @ApiModelProperty(value = "E-mail do usuário.")
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 8, message = "A senha precisa ter 8 ou mais caracteres")
    @ApiModelProperty(value = "Senha do usuário")
    private String senha;
}
