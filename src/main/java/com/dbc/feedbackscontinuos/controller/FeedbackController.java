package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;
import com.dbc.feedbackscontinuos.exception.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
