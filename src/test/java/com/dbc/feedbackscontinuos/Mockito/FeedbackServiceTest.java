package com.dbc.feedbackscontinuos.Mockito;

import com.dbc.feedbackscontinuos.dto.*;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FeedbackRepository;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.dbc.feedbackscontinuos.service.FeedbackService;
import com.dbc.feedbackscontinuos.service.FuncionarioService;
import com.dbc.feedbackscontinuos.service.TagsService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private  FeedbackRepository feedbackRepository;

    @Mock
    private  FuncionarioRepository funcionarioRepository;

    @Mock
    private  FuncionarioService funcionarioService;

    @Mock
    private  TagsService tagsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        ReflectionTestUtils.setField(feedbackService, "objectMapper",objectMapper);
    }

    @Test
    public void criaFeedBackComSucessoNotNull() throws RegraDeNegocioException {
        FeedbackEntity entity = new FeedbackEntity();
        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();


        TagsCreateDTO tags = new TagsCreateDTO();
        tags.setIdTag(1);

        List<TagsCreateDTO> listaTags = new ArrayList<>();
        listaTags.add(tags);

        doReturn(Optional.of(funcionarioEntity)).when(funcionarioRepository).findById(anyInt());

        FeedbacksCreateDTO feedbacksCreateDTO = new FeedbacksCreateDTO();
        feedbacksCreateDTO.setAnonimo(false);
        feedbacksCreateDTO.setConteudo("Teste");
        feedbacksCreateDTO.setIdFuncionarioDestino(2);
        feedbacksCreateDTO.setListaTags(listaTags);
        doReturn(entity).when(feedbackRepository).save(any());


        FeedbacksDTO feedbacksDTO = feedbackService.create(feedbacksCreateDTO, anyInt());
        Assert.assertNotNull(feedbacksDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void criaFeedBackComErroIdNaoEncontrado() throws RegraDeNegocioException {
        FeedbackEntity entity = new FeedbackEntity();
        FuncionarioEntity funcionarioEntity = new FuncionarioEntity();


        TagsCreateDTO tags = new TagsCreateDTO();
        tags.setIdTag(1);

        List<TagsCreateDTO> listaTags = new ArrayList<>();
        listaTags.add(tags);

//        doReturn(Optional.of(funcionarioEntity)).when(funcionarioRepository).findById(anyInt());

        FeedbacksCreateDTO feedbacksCreateDTO = new FeedbacksCreateDTO();
        feedbacksCreateDTO.setAnonimo(false);
        feedbacksCreateDTO.setConteudo("Teste");
        feedbacksCreateDTO.setIdFuncionarioDestino(2);
        feedbacksCreateDTO.setListaTags(listaTags);
        doReturn(entity).when(feedbackRepository).save(any());


        FeedbacksDTO feedbacksDTO = feedbackService.create(feedbacksCreateDTO, anyInt());
        Assert.assertNotNull(feedbacksDTO);
    }

}
