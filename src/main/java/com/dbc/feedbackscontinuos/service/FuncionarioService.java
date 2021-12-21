package com.dbc.feedbackscontinuos.service;

import com.dbc.feedbackscontinuos.dto.*;
import com.dbc.feedbackscontinuos.entity.FotoPerfilEntity;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FeedbackRepository;
import com.dbc.feedbackscontinuos.repository.FotoPerfilRepository;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final ObjectMapper objectMapper;
    private final FotoPerfilRepository fotoPerfilRepository;
    private final FeedbackRepository feedbackRepository;

    public FuncionarioDTO create(FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        FuncionarioEntity entity = objectMapper.convertValue(funcionarioCreateDTO, FuncionarioEntity.class);
        Optional<FuncionarioEntity> funcionario = funcionarioRepository.findByEmail(funcionarioCreateDTO.getEmail());

        if (funcionario.isPresent()) {
            throw new RegraDeNegocioException("Email já cadastrado.");
        } else if (!funcionarioCreateDTO.getEmail().contains("@dbccompany.com.br")) {
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
        FuncionarioDTO dto = objectMapper.convertValue(entity, FuncionarioDTO.class);
        byte[] encoded = new byte[0];
        try {
            encoded = Base64Utils.encode(toPrimitive(buscarFotoPorFuncionario(idFuncionario).getData()));
        } catch (RegraDeNegocioException e) {
            log.info(dto.getNome() + " não possui foto.");
        }
        dto.setFotoFuncionario(new String(encoded));
        return dto;
    }

    public List<FuncionarioDTO> list(Integer idFuncionario) {
        List<FuncionarioEntity> entities = funcionarioRepository.findFuncionariosExeto(idFuncionario);
        return entities.stream()
                .map(x -> {
                    FuncionarioDTO dto = objectMapper.convertValue(x, FuncionarioDTO.class);

                    byte[] encoded = new byte[0];
                    try {
                        encoded = Base64Utils.encode(toPrimitive(buscarFotoPorFuncionario(x.getIdFuncionario()).getData()));
                    } catch (RegraDeNegocioException e) {
                        log.info(x.getNome() + " não possui foto.");
                    }

                    dto.setFotoFuncionario(new String(encoded));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public FuncionarioComFeedbacksDTO getByIdComRecebidos(Integer idFuncionario) throws RegraDeNegocioException {
        FuncionarioEntity entity = findById(idFuncionario);
        FuncionarioComFeedbacksDTO dto = objectMapper.convertValue(entity, FuncionarioComFeedbacksDTO.class);
        byte[] encoded = new byte[0];
        try {
            encoded = Base64Utils.encode(toPrimitive(buscarFotoPorFuncionario(idFuncionario).getData()));
        } catch (RegraDeNegocioException e) {
            log.info(dto.getNome() + " não possui foto.");
        }
        dto.setFotoFuncionario(new String(encoded));
        List<FeedbacksOrigemDTO> feedbacksDTOS = feedbackRepository.findByIdFuncionarioDestinoVisivel(idFuncionario).stream()
                .map(x -> {
                    FeedbacksOrigemDTO feedDTO = objectMapper.convertValue(x, FeedbacksOrigemDTO.class);
                    try {
                        feedDTO.setFuncionarioOrigem(getById(x.getFuncionarioEntity().getIdFuncionario()));
                        feedDTO.setTags(x.getListaTags().stream().map(tagsEntity -> objectMapper.convertValue(tagsEntity, TagsDTO.class)).collect(Collectors.toList()));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return feedDTO;
                }).collect(Collectors.toList());
        dto.setRecebidos(feedbacksDTOS);
        return dto;
    }

    private byte[] toPrimitive(Byte[] imagem) {
        byte[] b2 = new byte[imagem.length];
        for (int i = 0; i < imagem.length; i++) {
            b2[i] = imagem[i];
        }
        return b2;
    }

    private FotoPerfilEntity buscarFotoPorFuncionario(Integer idFuncionario) throws RegraDeNegocioException {
        return fotoPerfilRepository.buscarFotoPorIdFuncionario(idFuncionario)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não possui foto."));
    }
}
