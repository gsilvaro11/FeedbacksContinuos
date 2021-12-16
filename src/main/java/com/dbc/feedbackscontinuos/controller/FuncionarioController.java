package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FuncionarioCreateDTO;
import com.dbc.feedbackscontinuos.dto.FuncionarioDTO;
import com.dbc.feedbackscontinuos.dto.LoginDTO;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.security.TokenService;
import com.dbc.feedbackscontinuos.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/funcionario")
@Validated
@RequiredArgsConstructor
public class FuncionarioController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final FuncionarioService funcionarioService;
    private final ObjectMapper objectMapper;

    @ApiOperation(value = "Onde o usuário insere e-mail e senha cadastrados e obtem um token JWT para acesso.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 400, message = "Há dados inseridos incorretamente ou pessoa não encontrada."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );
        Authentication authenticate = authenticationManager.authenticate(user);

        return tokenService.generateToken((FuncionarioEntity) authenticate.getPrincipal());
    }


    @ApiOperation(value = "Cria um novo cadastro de usuário;")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 400, message = "Há dados inseridos incorretamente."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @PostMapping("/cadastro")
    public FuncionarioDTO cadastrarFuncionario(@RequestBody @Valid FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        return funcionarioService.create(funcionarioCreateDTO);
    }


    @ApiOperation(value = "Mostra o usuário logado no momento.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @GetMapping("/usuario")
    public FuncionarioDTO getById() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);

        return funcionarioService.getById(idFuncionario);
    }


    @ApiOperation(value = "Lista todos os usuários exceto o usuário logado no momento.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @GetMapping("/funcionarios")
    public List<FuncionarioDTO> list(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        return funcionarioService.list(idFuncionario);
    }


    @ApiOperation(value = "Busca um usuário através do Id diretamente na URL.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deu certo! O comando funcionou."),
            @ApiResponse(code = 500, message = "Problema interno no sistema."),
    })
    @GetMapping("/{idFuncionario}")
    public FuncionarioDTO getById(HttpServletResponse response, @PathVariable("idFuncionario") Integer idFuncionario) throws RegraDeNegocioException {
       return funcionarioService.getById(idFuncionario);
    }

}
