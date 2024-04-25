package com.example.eldermate.controller;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.dto.MessageDTO;
import com.example.eldermate.dto.RequestDto1;
import com.example.eldermate.dto.ResponseDto1;
import com.example.eldermate.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "메세지 수신 api", description = "수신 내용, 시간, 발신자 정보를 받아 메세지 처리를 진행한다.")
    public ResponseEntity<Void> saveMessage(@RequestBody MessageDTO messageDTO, @AuthenticationPrincipal CustomUserDetails userDetails){
        messageService.saveMessage(messageDTO, userDetails);
        return ResponseEntity.ok().build();
    }
}
