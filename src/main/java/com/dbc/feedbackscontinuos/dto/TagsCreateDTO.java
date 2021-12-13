package com.dbc.feedbackscontinuos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TagsCreateDTO {
    @ApiModelProperty(value = "Id da tag")
    private Integer idTag;
}