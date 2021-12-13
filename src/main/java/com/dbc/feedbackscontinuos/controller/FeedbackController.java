package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FeedbacksCreateDTO;
import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.FeedbackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/feedbacks")
@Validated
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/enviados")
    @ApiOperation(value = "Lista feedbacks enviados do funcionario logado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback(s) listado(s) com sucesso."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public List<FeedbacksDTO> listarEnviados() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.listarEnviados(idFuncionario);

    }

    @GetMapping("/recebidos")
    @ApiOperation(value = "Lista feedbacks recebidos do funcionario logado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Feedback(s) listado(s) com sucesso."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public List<FeedbacksDTO> listarRecebids() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.listarRecebidos(idFuncionario);

    }

    @PostMapping("/postar")
    @ApiOperation(value = "Cria um novo feedback.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionário criado com sucesso."),
            @ApiResponse(code = 400, message = "Dados do funcionário inconsistentes."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public FeedbacksDTO create(@RequestBody @Valid FeedbacksCreateDTO feedbacksCreateDTO) throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.create(feedbacksCreateDTO, idFuncionario);
    }

}
