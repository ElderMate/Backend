package com.example.eldermate.service;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.UserRepository;
import com.example.eldermate.dto.JoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    //레파지토리에 접근할것이기 때문에 해당 레파지토리 주입받기
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO){
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        //레파지토리에 해당 아이디를 사용하고 있는 유저가 있는지 확인
        Boolean isExist = userRepository.existsByUsername(username);
        if(isExist){
            return;
        }

        //위의 DTO에서 받아온 데이터를 기반으로 새로운 엔티티 생성
        UserEntity data = new UserEntity();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");

        //레파지토리에 엔티티값을 저장
        userRepository.save(data);
    }
}
