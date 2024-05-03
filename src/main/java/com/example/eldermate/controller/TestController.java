package com.example.eldermate.controller;

import com.example.eldermate.jwt.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final JWTUtil jwtUtil;

    @GetMapping("/test")
    @Operation(summary = "테스트용 JWT 발급 api", description = "테스트에 해당하는 홍길동씨에 대한 JWT 토큰을 발긊한다.")
    public ResponseEntity<String> getJWT(){
        String token = jwtUtil.createJwt("01012341234", 1L, 600*600*10L);

        return ResponseEntity.ok("Bearer " + token);
    }
}
