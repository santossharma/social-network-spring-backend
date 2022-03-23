package com.socialnetwork.mappers;

import com.socialnetwork.dto.MessageDTO;
import com.socialnetwork.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class}) //imports existing mapper UserMapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    List<MessageDTO> messageToMessageDTOs(List<Message> messages);

    @Mapping(source = "user", target = "userDTO")
    MessageDTO messageToMessageDTO(Message message);

    Message messageDTOToMessage(MessageDTO messageDTO);
}
