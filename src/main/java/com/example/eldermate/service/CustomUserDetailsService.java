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

    //조회할 수 있기 위해, 데이터베이스에 접근할 수 있는 UserRepository를 주입
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);

        if(userData!=null){
            //CustomUserDetails는 데이터를 넘겨주기 때문에 DTO이다.
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
