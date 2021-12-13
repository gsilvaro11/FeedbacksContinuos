package com.dbc.feedbackscontinuos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class TagsDTO {

    @NotNull
    private Integer idTag;

    @NotNull(message = "Não pode ser nulo")
    @NotBlank(message = "Não pode estár em branco!")
    private String nomeTag;
}
