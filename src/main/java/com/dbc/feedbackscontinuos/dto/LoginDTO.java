package com.dbc.feedbackscontinuos.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {

    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    @Email
    private String email;

    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    private String senha;
}
