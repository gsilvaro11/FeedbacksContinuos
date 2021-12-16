package com.dbc.feedbackscontinuos;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteSenha {
    @Test
    public void encoder() {
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }
}
