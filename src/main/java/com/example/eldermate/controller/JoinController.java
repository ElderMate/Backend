package com.example.eldermate.controller;

import com.example.eldermate.dto.JoinDTO;
import com.example.eldermate.service.JoinService;
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
    public ResponseEntity<Void> joinProcess(@RequestBody JoinDTO joinDTO){
        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok().build();
    }
}
