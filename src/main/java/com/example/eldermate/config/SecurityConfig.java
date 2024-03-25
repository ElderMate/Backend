package com.example.eldermate.config;

import com.example.eldermate.jwt.JWTFilter;
import com.example.eldermate.jwt.JWTUtil;
import com.example.eldermate.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을
    //AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    //JWTUtil 객체 생성자 주입
    private final JWTUtil jwtUtil;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //비밀번호를 캐시로 암호화시키기 위함
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll() //해당 경로들은 모든 사람들이 접근 가능
                        .anyRequest().authenticated()); //그 외 접근은 로그인한 사용자만 접근 가능


        //JWTFilter 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //필터 추가 LoginFilter()는 인자를 받음
        // (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣은 인자와 jwtUtil인자를 받음)
        //따라서 등록 필요
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정 : stateless상태로 만들어주기!!
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}