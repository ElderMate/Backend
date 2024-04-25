package com.example.eldermate.controller;

import com.example.eldermate.dto.JoinDTO;
import com.example.eldermate.service.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    @Operation(summary = "회원가입 api", description = "유저 아이디, 비번, 성별, 이름, 생년월일 정보를 받아 회원가입 처리를 진행한다.")
    public ResponseEntity<Void> joinProcess(@RequestBody JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok().build();
    }
}
