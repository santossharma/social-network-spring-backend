package com.socialnetwork.controller;

import com.socialnetwork.dto.ImageDTO;
import com.socialnetwork.dto.MessageDTO;
import com.socialnetwork.services.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/community")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getCommunityMessages(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(communityService.getCommunityMessages(page));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDTO>> getCommunityImages(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(communityService.getCommunityImages(page));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDTO> postMessages(@RequestBody MessageDTO messageDTO) {
        // response code 201 (created)
        return ResponseEntity.created(URI.create("/v1/community/messages"))
                .body(communityService.postMessages(messageDTO));
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDTO> postImages(
            @RequestParam MultipartFile file, @RequestParam(value = "title") String title) {
        return ResponseEntity.created(URI.create("/v1/community/images"))
                .body(communityService.postImages(file, title));
    }
}
