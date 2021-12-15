package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.dto.FuncionarioCreateDTO;
import com.dbc.feedbackscontinuos.dto.FuncionarioDTO;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    public FuncionarioDTO create(FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException{
        FuncionarioEntity entity = objectMapper.convertValue(funcionarioCreateDTO, FuncionarioEntity.class);
        Optional<FuncionarioEntity> funcionario = funcionarioRepository.findByEmail(funcionarioCreateDTO.getEmail());

        if(funcionario.isPresent()){
            throw new RegraDeNegocioException("Email já cadastrado.");
        }else if (!funcionarioCreateDTO.getEmail().contains("@dbccompany.com.br")){
            throw new RegraDeNegocioException("Email precisa ter dominio: dbccompany!");
        }

        entity.setSenha(new BCryptPasswordEncoder().encode(funcionarioCreateDTO.getSenha()));
        entity.setDataRegistro(LocalDateTime.now().minusHours(3));

        funcionarioRepository.save(entity);

        return objectMapper.convertValue(entity, FuncionarioDTO.class);
    }

    public Optional<FuncionarioEntity> findByEmail(String email) {
        return funcionarioRepository.findByEmail(email);
    }

    public FuncionarioDTO findByIdDTO(Integer id) throws RegraDeNegocioException {
        FuncionarioEntity entity = findById(id);
        return objectMapper.convertValue(entity, FuncionarioDTO.class);
    }

    public FuncionarioEntity findById(Integer id) throws RegraDeNegocioException {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Funcionário não encontrado."));
    }

    public FuncionarioEntity findByIdForDownload(Integer id) throws RegraDeNegocioException {
        FuncionarioEntity entity = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Funcionário não encontrado."));

        if(entity.getData() == null){
            throw new RegraDeNegocioException("Foto não encontrada");
        }
        return entity;
    }

    public List<FuncionarioDTO> list(Integer idFuncionario){
        List<FuncionarioEntity> entities = funcionarioRepository.findFuncionariosExeto(idFuncionario);
        return entities.stream()
                .map(x -> objectMapper.convertValue(x, FuncionarioDTO.class))
                .collect(Collectors.toList());
    }

    public FuncionarioDTO storeFile(MultipartFile file, Integer idFuncionario) throws RegraDeNegocioException {
        FuncionarioEntity funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario n encontrado"));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new RegraDeNegocioException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            funcionario.setTipoImagem(file.getContentType());
            funcionario.setData(toObjects(file.getBytes()));

            return objectMapper.convertValue(funcionarioRepository.save(funcionario), FuncionarioDTO.class);
        } catch (RegraDeNegocioException | IOException ex) {
            throw new RegraDeNegocioException("Não salvou o arquivo.");
        }
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
