package com.example.eldermate.controller;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.dto.MessageDTO;
import com.example.eldermate.dto.RequestDto1;
import com.example.eldermate.dto.ResponseDto1;
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

    @PostMapping("/predict")
    public ResponseEntity<ResponseDto1> predictCategory(@RequestBody RequestDto1 requestDto1) {
        ResponseDto1 responseDto1 = messageService.predictCategory(requestDto1);
        return ResponseEntity.ok(responseDto1);
    }

    @PostMapping("/keywords")
    public ResponseEntity<ResponseDto1> extractKeywords(@RequestBody RequestDto1 requestDto1){
        ResponseDto1 responseDto1 = messageService.extractKeywords(requestDto1);
        return ResponseEntity.ok(responseDto1);
    }

}
