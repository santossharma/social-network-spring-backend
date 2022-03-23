package com.socialnetwork.services;

import com.socialnetwork.dto.ImageDTO;
import com.socialnetwork.dto.MessageDTO;
import com.socialnetwork.dto.UserDTO;
import com.socialnetwork.entities.Message;
import com.socialnetwork.entities.User;
import com.socialnetwork.exception.ApplicationException;
import com.socialnetwork.mappers.MessageMapper;
import com.socialnetwork.repositories.MessageRepository;
import com.socialnetwork.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private static final  int PAGE_SIZE = 10;

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;


    public List<MessageDTO> getCommunityMessages(UserDTO userDTO, int page) {

        User user = getUser(userDTO);

        List<Long> friendsUserIds = Optional.of(user.getFriends())
                .map(friend -> friend.stream().map(User::getId).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        friendsUserIds.add(user.getId());

        List<Message> messages = messageRepository.findCommunityMessages(friendsUserIds, PageRequest.of(page, PAGE_SIZE));

        return MessageMapper.INSTANCE.messageToMessageDTOs(messages);
    }

    public List<ImageDTO> getCommunityImages(int page) {
        return Arrays.asList(new ImageDTO(1L, "First Title", null),
                new ImageDTO(2L, "Second Title", null));
    }

    public MessageDTO postMessages(UserDTO userDTO, MessageDTO messageDTO) {
        User user = getUser(userDTO);
        Message message = MessageMapper.INSTANCE.messageDTOToMessage(messageDTO);
        message.setUser(user);

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        message.setCreatedDate(LocalDateTime.of(currentDate, currentTime));

        if(Objects.isNull(user.getMessages())) {
            user.setMessages(new ArrayList<>());
        }
        user.getMessages().add(message);

        Message savedMessage = messageRepository.save(message);

        return MessageMapper.INSTANCE.messageToMessageDTO(savedMessage);
    }

    public ImageDTO postImages(MultipartFile file, String title) {
        return new ImageDTO(3L, "New Title", null);
    }

    private User getUser(UserDTO userDTO) {
        return userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new ApplicationException("User Not Found", HttpStatus.NOT_FOUND));
    }
}
