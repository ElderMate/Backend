package com.example.eldermate.controller;

import com.example.eldermate.dto.*;
import com.example.eldermate.dto.message.MessageDTO;
import com.example.eldermate.dto.message.MessageResponseDto;
import com.example.eldermate.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    @Operation(summary = "메세지 전체 조회 api", description = "DB에 저장된 사용자 메세지 전체 조회를 진행한다.")
    public ResponseEntity<Slice<MessageResponseDto>> getAllMessage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false, defaultValue = "0") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int size){

        Slice<MessageResponseDto> responseDtos = messageService.getAllMessage(userDetails, pageNo, size);

        return  ResponseEntity.ok(responseDtos);
    }
}
