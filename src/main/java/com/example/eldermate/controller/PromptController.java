package com.example.eldermate.controller;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.dto.PromptEndRequestDto;
import com.example.eldermate.dto.PromptStartResponseDto;
import com.example.eldermate.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports/daily")
public class PromptController {
    private PromptService promptService;

    @PostMapping("/start")
    public ResponseEntity<PromptStartResponseDto> startPrompt(@AuthenticationPrincipal CustomUserDetails userDetails){

        PromptStartResponseDto responseDto = promptService.startPrompt(userDetails.getUserEntity());

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/end")
    public ResponseEntity<Void> endPrompt(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PromptEndRequestDto requestDto){

        promptService.endPrompt(userDetails.getUserEntity(), requestDto);

        return ResponseEntity.ok().build();
    }


}
