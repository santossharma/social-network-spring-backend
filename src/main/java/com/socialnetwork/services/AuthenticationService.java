package com.socialnetwork.services;

import com.socialnetwork.dto.CredentialsDTO;
import com.socialnetwork.dto.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
public class AuthenticationService {
    //private final PasswordEncoder passwordEncoder;

    /*public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }*/

    public UserDTO authenticate(CredentialsDTO credentialsDto) {
        String encodedMasterPassword = passwordEncoder().encode(CharBuffer.wrap("testPassword"));
        if (passwordEncoder().matches(CharBuffer.wrap(credentialsDto.getPassword()), encodedMasterPassword)) {
            return new UserDTO(1L, "Santosh", "Sharma", "login", "token");
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDTO findByLogin(String login) {
        if ("login".equals(login)) {
            return new UserDTO(1L, "Santosh", "Sharma", "login", "token");
        }
        throw new RuntimeException("Invalid login");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
