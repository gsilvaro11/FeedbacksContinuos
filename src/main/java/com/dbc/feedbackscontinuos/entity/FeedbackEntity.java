package com.dbc.feedbackscontinuos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@Entity(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback")
    private Integer idFeedback;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id_funcionario")
    private FuncionarioEntity funcionarioEntity;

    @Column(name = "id_funcionario_destino")
    private Integer idFuncionarioDestino;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "visivel")
    private Boolean visivel;

    @Column(name = "data_feedback")
    private LocalDateTime dataFeedback;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "feedback_tag",
            joinColumns = @JoinColumn(name = "id_feedback"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<TagsEntity> listaTags;


}
