package com.dbc.feedbackscontinuos.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TagsCreateDTO {
    @NotNull
    private Integer idTag;
}