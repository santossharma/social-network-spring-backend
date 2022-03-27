package com.socialnetwork.mappers;

import com.socialnetwork.dto.MessageDTO;
import com.socialnetwork.entities.Message;
import com.socialnetwork.entities.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class MessageMapperTest {

    private static MessageMapper messageMapper = new MessageMapperImpl();
    private static UserMapper userMapper = new UserMapperImpl();

    @BeforeAll
    public static void setup() {
        ReflectionTestUtils.setField(messageMapper, "userMapper", userMapper);
    }

    @Test
    public void  testMapSingleMessage() {
        // given
        Message message = Message.builder()
                .id(10L)
                .content("content")
                .user(User.builder().id(10L).firstName("santosh").lastName("sharma")
                        .birthDate(LocalDate.of(1980, 02, 12)).build())
                .build();
        // when
        MessageDTO messageDTO = messageMapper.messageToMessageDTO(message);

        // then
        assertAll(()-> {
                assertEquals(messageDTO.getContent(), message.getContent());
                assertEquals(messageDTO.getUserDTO().getId(), message.getUser().getId());
            }
        );

    }
}