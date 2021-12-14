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
@Entity(name = "fotoPerfil")
public class FotoPerfilEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto_perfil")
    private Integer idFotoPerfil;

    @Column(name = "tipo_foto_perfil")
    private String tipoFotoPerfil;

    @Column(name = "nome_foto_perfil")
    private String nomeFotoPerfil;

    @Column(name = "data")
    private Byte[] data;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_funcionario", referencedColumnName = "id_funcionario")
    private FuncionarioEntity funcionario;
}
