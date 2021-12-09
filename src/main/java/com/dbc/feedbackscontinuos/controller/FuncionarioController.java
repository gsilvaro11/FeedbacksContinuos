package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.config.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.dto.FuncionarioCreateDTO;
import com.dbc.feedbackscontinuos.dto.FuncionarioDTO;
import com.dbc.feedbackscontinuos.dto.LoginDTO;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.security.TokenService;
import com.dbc.feedbackscontinuos.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("login-cadastro")
@Validated
@RequiredArgsConstructor
public class FuncionarioController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final FuncionarioService funcionarioService;

    @PostMapping("/login")
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
    public FuncionarioDTO cadastrarFuncionario(@RequestBody @Valid FuncionarioCreateDTO funcionarioCreateDTO) throws RegraDeNegocioException {
        return funcionarioService.create(funcionarioCreateDTO);
    }
}
