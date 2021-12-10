package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FeedbacksCreateDTO;
import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.FeedbackService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/feedbacks")
@Validated
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/idFuncionario")
    public List<FeedbacksDTO> list(@RequestParam Integer idFuncionario) throws RegraDeNegocioException {
        return feedbackService.list(idFuncionario);
    }

    @PostMapping("/postar-feedback")
    public FeedbacksDTO create(@RequestParam Integer idFuncionario,@RequestParam Integer idFuncionarioDestino,
                               @RequestBody FeedbacksCreateDTO feedbacksCreateDTO) throws RegraDeNegocioException {
        return feedbackService.create(idFuncionario, idFuncionarioDestino, feedbacksCreateDTO);
    }

}
