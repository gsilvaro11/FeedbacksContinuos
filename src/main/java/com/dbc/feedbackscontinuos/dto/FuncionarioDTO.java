package com.dbc.feedbackscontinuos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;

import java.time.LocalDateTime;
import java.util.Base64;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioDTO {
    private Integer idFuncionario;
    private String nome;
    private String email;
    private String fotoFuncionario;
}
