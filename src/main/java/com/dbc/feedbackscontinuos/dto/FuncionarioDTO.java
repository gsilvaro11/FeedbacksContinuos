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
public class FuncionarioDTO {
    @NotNull
    private Integer idFuncionario;

    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    private String nome;

    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    @Email
    private String email;
}
