package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/feedbacks")
@Validated
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;


}
