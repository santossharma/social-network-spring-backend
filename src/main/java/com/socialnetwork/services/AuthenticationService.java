package com.socialnetwork.services;

import com.socialnetwork.dto.CredentialsDTO;
import com.socialnetwork.dto.UserDTO;
import com.socialnetwork.entities.User;
import com.socialnetwork.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.CharBuffer;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //private final PasswordEncoder passwordEncoder;

    /*public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }*/

    @Transactional
    public UserDTO authenticate(CredentialsDTO credentialsDto) {

        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(()-> new RuntimeException("User not found!"));

        String encodedMasterPassword = passwordEncoder().encode(CharBuffer.wrap("testPassword"));
        if (passwordEncoder().matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());

            return new UserDTO(user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getLogin(), user.getToken());
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDTO findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        return new UserDTO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getLogin(), user.getToken());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
