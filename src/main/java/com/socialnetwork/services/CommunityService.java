package com.socialnetwork.services;

import com.socialnetwork.dto.ImageDTO;
import com.socialnetwork.dto.MessageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Service
public class CommunityService {

    public List<MessageDTO> getCommunityMessages(int page) {
        return Arrays.asList(new MessageDTO(1L, "First Message"),
                new MessageDTO(2L, "Second Message"));
    }

    public List<ImageDTO> getCommunityImages(int page) {
        return Arrays.asList(new ImageDTO(1L, "First Title", null),
                new ImageDTO(2L, "Second Title", null));
    }

    public MessageDTO postMessages(MessageDTO messageDTO) {
        return new MessageDTO(3L, "Third Message");
    }

    public ImageDTO postImages(MultipartFile file, String title) {
        return new ImageDTO(3L, "New Title", null);
    }
}
