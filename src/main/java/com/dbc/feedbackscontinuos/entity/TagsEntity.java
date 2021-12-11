package com.dbc.feedbackscontinuos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "tags")
public class TagsEntity {
    @Id
    @Column(name = "id_tag")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTag;

    @Column(name = "nome_tag")
    private String nomeTag;

    @Column(name = "status")
    private Boolean status;

    @JsonIgnore
    @ManyToMany(mappedBy = "listaTags")
    private Set<FeedbackEntity> feedbacks;

}
