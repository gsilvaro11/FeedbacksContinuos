package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.config.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.dto.FuncionarioCreateDTO;
import com.dbc.feedbackscontinuos.dto.FuncionarioDTO;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;

    public FuncionarioDTO create(FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        FuncionarioEntity entity = objectMapper.convertValue(funcionarioCreateDTO, FuncionarioEntity.class);
        entity.setSenha(new BCryptPasswordEncoder().encode(funcionarioCreateDTO.getSenha()));
        funcionarioRepository.save(entity);
        return objectMapper.convertValue(entity, FuncionarioDTO.class);
    }
}
