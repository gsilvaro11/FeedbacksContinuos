package com.dbc.feedbackscontinuos.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "tags_feedback")
public class TagsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tags")
    private Integer idTag;

    @Column(name = "id_feedback")
    private Integer idFeedback;

    @Column(name = "nome_tag")
    private String nomeTag;

}
