package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.TagsDTO;
import com.dbc.feedbackscontinuos.service.TagsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {
    private final TagsService tagsService;


    @ApiOperation(value = "Lista todas as tags cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @GetMapping("/lista-tags")
    public List<TagsDTO> list() {
        return tagsService.list();
    }

}
