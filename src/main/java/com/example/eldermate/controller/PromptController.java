package com.example.eldermate.controller;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.dto.PromptEndRequestDto;
import com.example.eldermate.dto.PromptStartResponseDto;
import com.example.eldermate.service.PromptService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "데일리 리포트 시작 api", description = "JWT 토큰을 통해 데일리 리포트 파일을 생성후 시작한다.")
    public ResponseEntity<PromptStartResponseDto> startPrompt(@AuthenticationPrincipal CustomUserDetails userDetails){

        PromptStartResponseDto responseDto = promptService.startPrompt(userDetails.getUserEntity());

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/end")
    @Operation(summary = "데일리 리포트 종료 api", description = "JWT 토큰과 파일 이름을 통해 데일리 리포트 종료 처리를 진행한다.")
    public ResponseEntity<Void> endPrompt(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PromptEndRequestDto requestDto){

        promptService.endPrompt(userDetails.getUserEntity(), requestDto);

        return ResponseEntity.ok().build();
    }


}
