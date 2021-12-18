package com.dbc.feedbackscontinuos.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TagsDTO {
    private Integer idTag;
    private String nomeTag;
}
