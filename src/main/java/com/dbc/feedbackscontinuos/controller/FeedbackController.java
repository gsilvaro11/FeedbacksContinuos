package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FeedbacksCreateDTO;
import com.dbc.feedbackscontinuos.dto.FeedbacksDTO;
import com.dbc.feedbackscontinuos.entity.FeedbackEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.FeedbackService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/feedbacks")
@Validated
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/funcionario")
    public List<FeedbacksDTO> list() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);
        return feedbackService.list(idFuncionario);

    }


    @PostMapping("/postar-feedback")
    public FeedbacksDTO create(@RequestBody FeedbacksCreateDTO feedbacksCreateDTO) throws RegraDeNegocioException {
        return feedbackService.create(feedbacksCreateDTO);
    }

}
