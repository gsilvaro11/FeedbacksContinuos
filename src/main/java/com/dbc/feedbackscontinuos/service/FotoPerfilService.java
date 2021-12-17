package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FotoPerfilRepository;
import lombok.RequiredArgsConstructor;
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

        if (fotoPerfilRepository.verificaSeFuncionarioPossuiFoto(idFuncionario) > 0) {
            throw new RegraDeNegocioException("Usuário já possui foto cadastrada!");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
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


    private Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }
}
