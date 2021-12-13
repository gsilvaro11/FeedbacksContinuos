package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FuncionarioCreateDTO;
import com.dbc.feedbackscontinuos.dto.FuncionarioDTO;
import com.dbc.feedbackscontinuos.dto.LoginDTO;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.security.TokenService;
import com.dbc.feedbackscontinuos.service.FuncionarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/login")
    @ApiOperation(value = "Faz o login do funcionário.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionário logado com sucesso."),
            @ApiResponse(code = 400, message = "Dados do funcionário inconsistentes."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public String login(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );
        Authentication authenticate = authenticationManager.authenticate(user);

        return tokenService.generateToken((FuncionarioEntity) authenticate.getPrincipal());
    }

    @PostMapping("/cadastro")
    @ApiOperation(value = "Faz o cadastro do funcionário.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionário cadastrado com sucesso."),
            @ApiResponse(code = 400, message = "Dados do funcionário inconsistentes."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public FuncionarioDTO cadastrarFuncionario(@RequestBody @Valid FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        return funcionarioService.create(funcionarioCreateDTO);
    }

    @GetMapping("/usuario")
    @ApiOperation(value = "Lista o funcionário logado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionário listado com sucesso."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public FuncionarioDTO getById() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();

        Integer idFuncionario = Integer.valueOf(principal);

        return funcionarioService.getById(idFuncionario);
    }

    @GetMapping("/funcionarios")
    @ApiOperation(value = "Lista nome e email dos funcionários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionários listados com sucesso."),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema.")
    })
    public List<FuncionarioDTO> list(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        return funcionarioService.list(idFuncionario);
    }

}
