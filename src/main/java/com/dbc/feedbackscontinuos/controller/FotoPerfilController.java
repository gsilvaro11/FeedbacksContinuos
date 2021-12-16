package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FotoPerfilDTO;
import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FotoPerfilRepository;
import com.dbc.feedbackscontinuos.service.FotoPerfilService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Base64Utils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/foto-perfil")
@RequiredArgsConstructor
public class FotoPerfilController {
    private static final Logger logger = LoggerFactory.getLogger(FotoPerfilController.class);

    private final FotoPerfilService fotoPerfilService;
    private final FotoPerfilRepository fotoPerfilRepository;

    @PostMapping("/upload-foto")
    public FotoPerfilDTO uploadFoto(@RequestPart("foto")MultipartFile file) throws RegraDeNegocioException {
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

    @GetMapping("/download-foto")
    public HttpEntity getFile(HttpServletResponse response) throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        FotoPerfilEntity entity = fotoPerfilRepository.buscarFotoPorIdFuncionario(idFuncionario)
                .orElseThrow(() -> new RegraDeNegocioException("Imagem não encontrada!"));

        byte[] encoded = Base64Utils.encode(fotoPerfilService.toPrimitive(entity.getData()));

        try (InputStream inputStream = new ByteArrayInputStream(encoded)) {
            StreamUtils.copy(inputStream, response.getOutputStream());
            response.setContentType(MediaType.ALL_VALUE);
        } catch (IOException e) {
            // handle
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/download-foto/{idFuncionario}")
    public HttpEntity getFileForId(HttpServletResponse response, @PathVariable("idFuncionario") Integer idFuncionario) throws RegraDeNegocioException { ;

        FotoPerfilEntity entity = fotoPerfilRepository.buscarFotoPorIdFuncionario(idFuncionario)
                .orElseThrow(() -> new RegraDeNegocioException("Imagem não encontrada!"));

        byte[] encoded = Base64Utils.encode(fotoPerfilService.toPrimitive(entity.getData()));

        try (InputStream inputStream = new ByteArrayInputStream(encoded)) {
            StreamUtils.copy(inputStream, response.getOutputStream());
            response.setContentType(MediaType.ALL_VALUE);
        } catch (IOException e) {
            // handle
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
