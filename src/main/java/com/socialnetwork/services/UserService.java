package com.socialnetwork.services;

import com.socialnetwork.dto.*;
import com.socialnetwork.entities.User;
import com.socialnetwork.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ProfileDTO getProfile(Long userId) {
        User user = getUser(userId);
        return new ProfileDTO(new UserSummaryDTO(user.getId(), user.getFirstName(), user.getLastName()),
                null,
                null,
                null);
    }

    public void addFriend(UserDTO userDTO, Long friendId) {
        User user = getUser(userDTO.getId());

        User newFriend = getUser(friendId);

        if(user.getFriends() == null) {
            user.setFriends(new ArrayList<>());
        }
        user.getFriends().add(newFriend);

        userRepository.save(user);

    }

    public List<UserSummaryDTO> searchUsers(String term) {
        List<User> users = userRepository.search("%" + term + "%");
        List<UserSummaryDTO> usersToBeReturned = new ArrayList<>();

        users.forEach(user ->
                usersToBeReturned.add(new UserSummaryDTO(user.getId(), user.getFirstName(), user.getLastName()))
        );

        return usersToBeReturned;
    }

    public UserDTO signUp(SignUpDTO userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("Login already exists");
        }

        User user = new User(null,
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getLogin(),
                passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())),
                UUID.randomUUID().toString(),
                null,
                null,
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);

        return new UserDTO(savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getLogin(),
                savedUser.getToken());
    }

    public void signOut(UserDTO userDTO) {
        User user = getUser(userDTO.getId());

        user.setToken(null);
        userRepository.save(user);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
