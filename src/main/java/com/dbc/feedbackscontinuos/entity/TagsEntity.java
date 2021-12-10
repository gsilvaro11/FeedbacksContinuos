package com.dbc.feedbackscontinuos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_feedback", referencedColumnName = "id_feedback")
    private FeedbackEntity feedbackEntity;

}
