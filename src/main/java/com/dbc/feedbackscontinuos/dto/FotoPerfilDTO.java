package com.dbc.feedbackscontinuos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoPerfilDTO {
    private String tipoFotoPerfil;
    private String nomeFotoPerfil;
    private String downloadUri;
    private Long size;
}
