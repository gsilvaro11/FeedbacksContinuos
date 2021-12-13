package com.dbc.feedbackscontinuos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioCreateDTO {

    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    private String nome;

    @Email
    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    private String email;

    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    private String senha;
}
