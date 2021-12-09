package com.dbc.feedbackscontinuos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "feedback")
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback")
    private Integer idFeedback;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id_funcionario")
    private FuncionarioEntity idFuncionario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario_destino", referencedColumnName = "id_funcionario")
    private FuncionarioEntity idFuncionarioDestino;

    @Column(name = "conteudo")
    private String conteudo;

    @Column(name = "deletado")
    private Boolean deletado;

    @Column(name = "visivel")
    private Boolean visivel;

}
