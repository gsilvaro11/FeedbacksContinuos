package com.dbc.feedbackscontinuos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsDTO {
    private Integer idTag;
    private Integer idFeedback;
    private String nomeTag;

}
