package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.TagsCreateDTO;
import com.dbc.feedbackscontinuos.dto.TagsDTO;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.TagsService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {
    private final TagsService tagsService;

    @PostMapping("/postar-tag")
    public List<TagsDTO> create(@RequestBody TagsCreateDTO tagsCreateDTO) throws RegraDeNegocioException {
        return tagsService.create(tagsCreateDTO);
    }

}
