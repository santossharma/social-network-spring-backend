package com.socialnetwork.services;

import com.socialnetwork.dto.*;
import com.socialnetwork.entities.User;
import com.socialnetwork.exception.ApplicationException;
import com.socialnetwork.mappers.UserMapper;
import com.socialnetwork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public ProfileDTO getProfile(Long userId) {
        User user = getUser(userId);
        return UserMapper.INSTANCE.toProfileDTO(user);
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

    public UserDTO signUp(SignUpDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByLogin(userDTO.getLogin());

        if (optionalUser.isPresent()) {
            throw new ApplicationException("Login already exists", HttpStatus.BAD_REQUEST);
        }
        User user = UserMapper.INSTANCE.signUpToUser(userDTO);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDTO.getPassword())));
        user.setToken(UUID.randomUUID().toString());

        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toUserDTO(savedUser);
    }

    public void signOut(UserDTO userDTO) {
        User user = getUser(userDTO.getId());

        user.setToken(null);
        userRepository.save(user);
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("User not found", HttpStatus.NOT_FOUND));
    }
}
