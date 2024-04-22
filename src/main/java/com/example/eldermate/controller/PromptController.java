package com.example.eldermate.controller;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.dto.PromptStartResponseDto;
import com.example.eldermate.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
