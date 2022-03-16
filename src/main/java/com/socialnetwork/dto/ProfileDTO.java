package com.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private UserSummaryDTO userDTO;
    private List<UserSummaryDTO> friends;
    private List<MessageDTO> messages;
    private List<ImageDTO> images;
}
