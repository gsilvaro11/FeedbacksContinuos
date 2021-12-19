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

    @ApiOperation(value = "Lista todos os feedbacks enviados do usuário logado no momento.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @GetMapping("/enviados")
    public List<FeedbacksDTO> listarEnviados() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.listarEnviados(idFuncionario);

    }


    @ApiOperation(value = "Lista todos os feedbacks recebidos pelo usuário logado no momento.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @GetMapping("/recebidos")
    public List<FeedbacksDTO> listarRecebidos() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.listarRecebidos(idFuncionario);

    }


    @ApiOperation(value = "Cria um novo feedback tendo como origem o usuário logado no momento.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 400, message = "Há dados inseridos incorretamente ou pessoa não encontrada."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @PostMapping("/postar")
    public FeedbacksDTO create(@RequestBody @Valid FeedbacksCreateDTO feedbacksCreateDTO) throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.create(feedbacksCreateDTO, idFuncionario);
    }


    @ApiOperation(value = "Muda um feedback de visivel para invisivel e vice-versa.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 400, message = "Há dados inseridos incorretamente ou pessoa não encontrada."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @PutMapping("/alterar-visivel/{feedbackId}")
    public FeedbacksDTO alterarVisivel(@PathVariable("feedbackId") Integer feedbackId) throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.updateVisivel(feedbackId, idFuncionario);
    }
}
