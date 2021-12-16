package com.dbc.feedbackscontinuos.Mockito;

import com.dbc.feedbackscontinuos.dto.FuncionarioCreateDTO;
import com.dbc.feedbackscontinuos.dto.FuncionarioDTO;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.dbc.feedbackscontinuos.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FuncionarioServiceTest {

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        ReflectionTestUtils.setField(funcionarioService, "objectMapper",objectMapper);
    }

    @Test
    public void criaUmFuncionarioComSucesso() throws RegraDeNegocioException {
        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();
        FuncionarioCreateDTO funcionarioCreateDTO = new FuncionarioCreateDTO();

        funcionarioCreateDTO.setEmail("gui@dbccompany.com.br");
        funcionarioCreateDTO.setSenha("senha");
        funcionarioCreateDTO.setNome("teste");
        doReturn(funcionarioEntity).when(funcionarioRepository).save(any());

        FuncionarioDTO funcionario = funcionarioService.create(funcionarioCreateDTO);
        Assert.assertNotNull(funcionario);
        Assert.assertEquals("gui@dbccompany.com.br", funcionario.getEmail());
        Assert.assertEquals("teste", funcionario.getNome());

    }

    // Teste para verificar trativa de erro no dominio do email!.
    @Test(expected = RegraDeNegocioException.class)
    public void criaUmFuncionarioComErroNoEmail() throws RegraDeNegocioException {
        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();
        FuncionarioCreateDTO funcionarioCreateDTO = new FuncionarioCreateDTO();

        funcionarioCreateDTO.setEmail("gui@.com.br");
        funcionarioCreateDTO.setSenha("senha");
        funcionarioCreateDTO.setNome("teste");
        doReturn(funcionarioEntity).when(funcionarioRepository).save(any());

        FuncionarioDTO funcionario = funcionarioService.create(funcionarioCreateDTO);

    }
}
