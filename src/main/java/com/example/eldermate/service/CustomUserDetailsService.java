package com.example.eldermate.service;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 직접 UserEntity를 조회
        UserEntity userEntity = userRepository.findByUsername(username);

        // userEntity가 null인 경우 UsernameNotFoundException 발생
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        System.out.println("로그인한 User 정보 : "+userEntity.toString()); //로딩된 userEntity 값을 확인

        // UserEntity가 존재하는 경우, CustomUserDetails 객체 반환
        return new CustomUserDetails(userEntity);
    }
}
