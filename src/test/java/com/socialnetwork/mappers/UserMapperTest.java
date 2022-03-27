package com.socialnetwork.mappers;

import com.socialnetwork.dto.UserDTO;
import com.socialnetwork.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private static UserMapper userMapper;

    @BeforeAll
    public static void init() {
        userMapper = new UserMapperImpl();
    }

    @Test
    public void testUserMapper() {
        // Given
        User user = User.builder()
                .id(1L)
                .firstName("Santosh")
                .lastName("Sharma")
                .login("login")
                .password("pass")
                .token("token")
                .createdDate(LocalDateTime.now())
                .birthDate(LocalDate.of(1980, 02, 12))
                .build();
        // When
        UserDTO userDTO = UserMapper.INSTANCE.toUserDTO(user);

        // Then

        assertAll(()-> {
            assertEquals(user.getFirstName(), userDTO.getFirstName());
            assertEquals(user.getLastName(), userDTO.getLastName());
            assertEquals(user.getLogin(), userDTO.getLogin());
            assertEquals(Period.between(LocalDate.now(), user.getBirthDate()).getYears(), userDTO.getAge());
        });
    }
}
