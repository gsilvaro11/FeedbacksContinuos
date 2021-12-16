package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FotoPerfilDTO;
import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FotoPerfilRepository;
import com.dbc.feedbackscontinuos.service.FotoPerfilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/foto-perfil")
@RequiredArgsConstructor
public class FotoPerfilController {
    private static final Logger logger = LoggerFactory.getLogger(FotoPerfilController.class);

    private final FotoPerfilService fotoPerfilService;

    @PostMapping("/upload-foto")
    public FotoPerfilDTO uploadFoto(@RequestPart("foto")MultipartFile file) throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        FotoPerfilEntity fotoPerfil = fotoPerfilService.storeFile(file, idFuncionario);

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFoto/")
                .path(fotoPerfil.getFuncionario().toString())
                .toUriString();

        return new FotoPerfilDTO(file.getContentType(), fotoPerfil.getNomeFotoPerfil(), downloadUri, file.getSize());
    }

    @GetMapping("/download-foto")
    public ResponseEntity<Resource> downloadFoto() {
       return fotoPerfilService.downloadFoto();
    }
}
