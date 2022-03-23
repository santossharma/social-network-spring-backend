package com.socialnetwork.services;

import com.socialnetwork.dto.CredentialsDTO;
import com.socialnetwork.dto.UserDTO;
import com.socialnetwork.entities.User;
import com.socialnetwork.mappers.UserMapper;
import com.socialnetwork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.CharBuffer;
import java.util.UUID;

@Service
@RequiredArgsConstructor // instead of constructor injection
public class AuthenticationService {
    private final UserRepository userRepository;
    //private final UserMapper userMapper;

    //private final PasswordEncoder passwordEncoder;

    /*public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }*/

    @Transactional
    public UserDTO authenticate(CredentialsDTO credentialsDto) {

        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(()-> new RuntimeException("User not found!"));

        if (passwordEncoder().matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());

            return UserMapper.INSTANCE.toUserDTO(user);
        }
        throw new RuntimeException("Invalid password");
    }

    public UserDTO findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        return UserMapper.INSTANCE.toUserDTO(user);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
