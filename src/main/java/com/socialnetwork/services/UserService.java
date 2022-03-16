package com.socialnetwork.services;

import com.socialnetwork.dto.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    public ProfileDTO getProfile(Long userId) {
        return new ProfileDTO(new UserSummaryDTO(1L, "Santosh", "Sharma"),
                Arrays.asList(new UserSummaryDTO(2L, "Git", "Hub")),
                Arrays.asList(new MessageDTO(1L, "My message")),
                Arrays.asList(new ImageDTO(1L, "Title", null)));
    }

    public void addFriend(Long friendId) {
        return;
    }

    public List<UserSummaryDTO> searchUsers(String term) {
        return Arrays.asList(new UserSummaryDTO(1L, "Santosh", "Sharma"),
                new UserSummaryDTO(2L, "Git", "Hub"));
    }

    public UserDTO signUp(SignUpDTO user) {
        return new UserDTO(1L, "Santosh", "Sharma", "login", "token");
    }

    public void signOut(UserDTO user) {
        // TODO
    }

}
