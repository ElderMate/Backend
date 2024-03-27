package com.example.eldermate.jwt;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.entity.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
//form로그인방식을 disable시켰기 때문에, UsernamePasswordAuthenticationFilter를 커스텀해서 등록해야 함.
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("LoginFilter : 로그인 시도중...");

        // 1 사용자의 userName, password 를 받는다
        try {
            // json 데이터를 파싱해줌
            ObjectMapper objectMapper = new ObjectMapper();
            UserEntity userEntity = objectMapper.readValue(request.getInputStream(), UserEntity.class);
            // 유저에 정보가 잘 담겼는지 print 해보기
            System.out.println(userEntity);
            // 토큰 생성(userName, password 를 담은)
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userEntity.getUsername(),userEntity.getPassword());
            // authenticationManager.authenticate() 인증 수행하기
            // 그리고 PrincipalDetailsService 의 loadByUsername() 함수가 실행됨
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            System.out.println(request.getInputStream()); // request.getInputStream() 은 HttpServletRequest 객체로부터 입력 스트림을 가져오는 데 사용
            // 클라이언트 또는 다른 웹브라우저에서 서버로 전송된 데이터를 읽는데 사용할 수 있는 InputStream 객체
            // authenticate 객체가 session 영역에 저장됨
            CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
            System.out.println("==================로그인 완료=================="); // 구분선
            System.out.println("principalDetails userName :"+customUserDetails.getUsername());
            System.out.println("==============================================="); // 구분선
            return authenticate;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //로그인 성공시 실행하는 메소드 (성공시 JWT를 발급)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        //UserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        //뽑아온 username값, 토큰유효시간을 초기화
        String token = jwtUtil.createJwt(username, customUserDetails.getId(), 600*600*10L);
        //헤더에 담아서 토큰 응답, (키값,인증방식+토큰)
        response.addHeader("Authorization", "Bearer " + token);

    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        //로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
    }

}
