package com.socialnetwork.services;

import com.socialnetwork.dto.ProfileDTO;
import com.socialnetwork.dto.SignUpDTO;
import com.socialnetwork.dto.UserDTO;
import com.socialnetwork.dto.UserSummaryDTO;
import com.socialnetwork.entities.User;
import com.socialnetwork.enums.UserStatus;
import com.socialnetwork.mappers.UserMapper;
import com.socialnetwork.mappers.UserMapperImpl;
import com.socialnetwork.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    public void testProfileData() {
        // given
         Optional<User> user = Optional.of(User.builder()
                 .id(10L)
                 .firstName("Santosh")
                 .lastName("Sharma")
                 .login("login")
                 .password("pass")
                 .token("token")
                 .birthDate(LocalDate.of(1980, 02,12))
                 .createdDate(LocalDateTime.now())
                 .build());
        when(userRepository.findById(10L)).thenReturn(user);

        // when
        ProfileDTO profile = userService.getProfile(10L);

        //then
        verify(userRepository).findById(10L);
        assertAll(()-> {
            assertTrue("Santosh".equals(profile.getUserDTO().getFirstName()));
            assertTrue("Sharma".equals(profile.getUserDTO().getLastName()));
        });
    }

    @Test
    void testAddFriend() {
        LocalDate birthDate = LocalDate.of(1980, 2, 12);
        Date dob = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // given
        UserDTO userDto = new UserDTO(1L, "Santosh", "Sharma", "login", "token",
                UserStatus.CREATED, 42,
                dob);

        long friendId = 10L;

        Optional<User> user = Optional.of(User.builder()
                .id(1L)
                .firstName("Santosh")
                .lastName("Sharma")
                .login("login")
                .password("pass")
                .createdDate(LocalDateTime.now())
                .build());
        when(userRepository.findById(1L)).thenReturn(user);

        Optional<User> friend = Optional.of(User.builder()
                .id(10L)
                .firstName("first_friend")
                .lastName("last_friend")
                .login("login_friend")
                .password("pass")
                .createdDate(LocalDateTime.now())
                .build());
        when(userRepository.findById(10L)).thenReturn(friend);

        // when
        userService.addFriend(userDto, friendId);

        // then
        verify(userRepository).save(argThat(argument -> argument.getFriends().size() == 1));
    }

    @Test
    void testSignUp() {
        LocalDate birthDate = LocalDate.of(1980, 2, 12);
        Date dob = Date.from(birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // given
        SignUpDTO signUpDto = new SignUpDTO("Santosh", "Sharma", "login",
                "pass".toCharArray(), dob);

        User user = User.builder()
                .id(10L)
                .firstName("Santosh")
                .lastName("Sharma")
                .login("login")
                .password("pass")
                .createdDate(LocalDateTime.now())
                .birthDate(LocalDate.of(1980, 02, 12))
                .build();

        when(userRepository.findByLogin("login"))
                .thenReturn(Optional.empty());
        when(userRepository.save(argThat(argument -> argument.getFirstName().equals(signUpDto.getFirstName())
                && argument.getLastName().equals(signUpDto.getLastName()))))
                .thenReturn(user);
        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        // when
        UserDTO userDto = userService.signUp(signUpDto);

        // then
        assertAll(() -> {
            assertEquals(signUpDto.getFirstName(), userDto.getFirstName());
            assertEquals(signUpDto.getLastName(), userDto.getLastName());
        });
    }

    @Test
    void testSearchUser() {
        // given
        String term = "term";

        when(userRepository.search("%term%"))
                .thenReturn(Arrays.asList(User.builder()
                        .id(21L)
                        .firstName("first")
                        .lastName("last")
                        .login("login")
                        .password("pass")
                        .createdDate(LocalDateTime.now())
                        .build()));

        // when
        List<UserSummaryDTO> users = userService.searchUsers(term);

        // then
        assertAll(() -> {
            assertEquals(1, users.size());
            assertEquals("first", users.get(0).getFirstName());
        });
        verify(userRepository).search("%term%");
    }

}