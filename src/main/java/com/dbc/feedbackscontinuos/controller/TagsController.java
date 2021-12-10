package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.TagsDTO;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.TagsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {
    private final TagsService tagsService;

    @PostMapping("/envia-tag")
    public TagsDTO create(@RequestBody TagsDTO tagsDTO) throws RegraDeNegocioException {
        return tagsService.create(tagsDTO);
    }

}
