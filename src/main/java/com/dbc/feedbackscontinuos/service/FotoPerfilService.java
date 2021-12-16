package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FotoPerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FotoPerfilService {
    private final FotoPerfilRepository fotoPerfilRepository;
    private final FuncionarioService funcionarioService;

    public FotoPerfilEntity storeFile(MultipartFile file, Integer idFuncionario) throws RegraDeNegocioException {

        if(fotoPerfilRepository.verificaSeFuncionarioPossuiFoto(idFuncionario) > 0){
            throw new RegraDeNegocioException("Usuário já possui foto cadastrada!");
        };

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new RegraDeNegocioException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            FotoPerfilEntity entity = new FotoPerfilEntity();
            entity.setNomeFotoPerfil(fileName);
            entity.setTipoFotoPerfil(file.getContentType());
            entity.setData(toObjects(file.getBytes()));
            entity.setFuncionario(funcionarioService.findById(idFuncionario));
            return fotoPerfilRepository.save(entity);
        } catch (RegraDeNegocioException | IOException ex) {
            throw new RegraDeNegocioException("Não salvou o arquivo.");
        }
    }

    public ResponseEntity<Resource> downloadFoto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        FotoPerfilEntity entity = fotoPerfilRepository.buscarFotoPorIdFuncionario(idFuncionario);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(entity.getTipoFotoPerfil()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+entity.getNomeFotoPerfil())
                .body(new ByteArrayResource(toPrimitive(entity.getData())));
    }


    private Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }


    public byte[] toPrimitive(Byte[] imagem) {
        byte[] b2 = new byte[imagem.length];
        for (int i = 0; i < imagem.length; i++)
        {
            b2[i] = imagem[i];
        }
        return b2;
    }


}
