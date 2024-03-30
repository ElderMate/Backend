package com.example.eldermate.controller;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.dto.MessageDTO;
import com.example.eldermate.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/msg")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/receive")
    public ResponseEntity<Void> saveMessage(@RequestBody MessageDTO messageDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        messageService.saveMessage(messageDTO, userDetails);
        return ResponseEntity.ok().build();
    }

}
