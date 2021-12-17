package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FotoPerfilDTO;
import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.FotoPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/foto-perfil")
@RequiredArgsConstructor
public class FotoPerfilController {
    private final FotoPerfilService fotoPerfilService;


    @PostMapping("/upload-foto")
    public FotoPerfilDTO uploadFoto(@RequestPart("foto") MultipartFile file) throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        FotoPerfilEntity fotoPerfil = fotoPerfilService.storeFile(file, idFuncionario);

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFoto/")
                .path(fotoPerfil.getIdFotoPerfil().toString())
                .toUriString();

        return new FotoPerfilDTO(file.getContentType(), fotoPerfil.getNomeFotoPerfil(), downloadUri, file.getSize());
    }
}
