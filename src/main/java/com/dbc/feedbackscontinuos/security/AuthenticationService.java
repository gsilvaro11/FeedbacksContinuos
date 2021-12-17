package com.dbc.feedbackscontinuos.security;

import com.dbc.feedbackscontinuos.entity.FuncionarioEntity;
import com.dbc.feedbackscontinuos.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final FuncionarioService funcionarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<FuncionarioEntity> funcionario = funcionarioService.findByEmail(email);

        if (funcionario.isPresent()) {
            return (UserDetails) funcionario.get();
        }

        return null;
    }
}
