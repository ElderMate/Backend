package com.example.eldermate.controller;

import com.example.eldermate.entity.Message;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.jwt.JWTUtil;
import com.example.eldermate.repository.MessageRepository;
import com.example.eldermate.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final JWTUtil jwtUtil;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @GetMapping("/test")
    @Operation(summary = "테스트용 JWT 발급 api", description = "테스트에 해당하는 홍길동씨에 대한 JWT 토큰을 발급한다.")
    public ResponseEntity<String> getJWT(){
        String token = jwtUtil.createJwt("01012341234", 1L, 600*600*10L);

        return ResponseEntity.ok("Bearer " + token);
    }

    @GetMapping("/reset")
    @Operation(summary = "테스트용 DB 초기화 api", description = "테스트 사용자 문자 상태를 초기 상태로 변경한다")
    @Transactional
    public ResponseEntity<Void> resetDB(){
        UserEntity user = userRepository.findById(1L).orElseThrow();

        List<Message> messages = messageRepository.findAllByUser(user);

        for(Message message : messages){
            message.setConfirm(false);
            message.setIsProblem(false);
            message.setProblemReason("");
        }

        return ResponseEntity.ok().build();
    }

}
