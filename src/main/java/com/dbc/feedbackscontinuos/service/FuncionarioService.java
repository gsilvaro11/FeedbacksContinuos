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

    public FuncionarioDTO getById(Integer idFuncionario) throws RegraDeNegocioException {
        FuncionarioEntity entity = findById(idFuncionario);
        return objectMapper.convertValue(entity, FuncionarioDTO.class);
    }

    public List<FuncionarioDTO> list(Integer idFuncionario){
        List<FuncionarioEntity> entities = funcionarioRepository.findFuncionariosExeto(idFuncionario);
        return entities.stream()
                .map(x -> objectMapper.convertValue(x, FuncionarioDTO.class))
                .collect(Collectors.toList());
    }


}
