package com.dbc.feedbackscontinuos.controller;

import com.dbc.feedbackscontinuos.dto.FuncionarioCreateDTO;
import com.dbc.feedbackscontinuos.dto.FuncionarioDTO;
import com.dbc.feedbackscontinuos.dto.LoginDTO;
import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.exceptions.RegraDeNegocioException;
import com.dbc.feedbackscontinuos.repository.FuncionarioRepository;
import com.dbc.feedbackscontinuos.security.TokenService;
import com.dbc.feedbackscontinuos.service.FuncionarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/funcionario")
@Validated
@RequiredArgsConstructor
public class FuncionarioController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
    private final FuncionarioService funcionarioService;
    private final FuncionarioRepository funcionarioRepository;

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

        return funcionarioService.findByIdDTO(idFuncionario);
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
    public FuncionarioDTO getById(@PathVariable("idFuncionario") Integer idFuncionario) throws RegraDeNegocioException {
       return funcionarioService.findByIdDTO(idFuncionario);
    }

    @PostMapping("/uploadFoto")
    public FuncionarioDTO uploadFoto(@RequestPart("foto")MultipartFile file) throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        FuncionarioDTO funcionarioDTO = funcionarioService.storeFile(file, idFuncionario);

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFoto/")
                .path(file.getName())
                .toUriString();

        return funcionarioDTO;
    }

    @GetMapping("/download-foto")
    public ResponseEntity<Resource> downloadFoto() throws RegraDeNegocioException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String) authentication.getPrincipal();
        Integer idFuncionario = Integer.valueOf(principal);

        FuncionarioEntity entity = funcionarioService.findByIdForDownload(idFuncionario);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(entity.getTipoImagem()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= "+entity.getNome())
                .body(new ByteArrayResource(funcionarioService.toPrimitive(entity.getData())));
    }


}
