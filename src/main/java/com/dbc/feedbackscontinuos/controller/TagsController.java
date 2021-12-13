package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.TagsDTO;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.service.TagsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {
    private final TagsService tagsService;

    @GetMapping("/lista-tags")
    @ApiOperation(value = "Lista as tags cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tags listadas com sucesso."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public List<TagsDTO> list(){
        return tagsService.list();
    }

    @GetMapping("/tag")
    public List<TagsDTO> listPorNome(@RequestParam String tag) {
        return tagsService.listByName(tag.toUpperCase(Locale.ROOT));
    }

}
