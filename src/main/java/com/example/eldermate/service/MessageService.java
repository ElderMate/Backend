package com.example.eldermate.service;

import com.example.eldermate.dto.CustomUserDetails;
import com.example.eldermate.dto.MessageDTO;
import com.example.eldermate.dto.RequestDto1;
import com.example.eldermate.dto.ResponseDto1;
import com.example.eldermate.entity.Message;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final KeywordService keywordService;


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; //객체 -> Json으로 변경
    private final String HOST = "http://127.0.0.1:8000";

    public void saveMessage(MessageDTO messageDTO, CustomUserDetails userDetails) {
        UserEntity user = userDetails.getUserEntity();
        log.info("MessageService : {} ", user.toString());
        log.info("Received MessageDTO : {}", messageDTO.toString());

        // DTO에서 받은 time 문자열을 LocalDateTime으로 변환
        //LocalDateTime date = LocalDateTime.parse(messageDTO.getTime(), DateTimeFormatter.ISO_DATE);

        Message message = Message.builder()
                .pNum(messageDTO.getPNum())
                .msg(messageDTO.getMsg())
                .time(messageDTO.getTime())
                .user(user)
                .build();

        log.info("MessageDTO : {} ", message.toString());

        // 외부 API로부터 category 예측 받기
        RequestDto1 requestDto1 = new RequestDto1(message.getMsg()); // msg를 기반으로 DTO 생성
        ResponseDto1 responseDto1 = predictCategory(requestDto1); // category 예측 받기

        // 받은 category 값을 메시지에 설정
        message.setCategory(responseDto1.response());

        messageRepository.save(message);
        keywordService.handleCategoryResponse(message, responseDto1.response());
    }

    // 외부 api로 요청 + 응답을 가져오는 메서드
    private <T> ResponseEntity<T> requestToApi(String endPoint, String body, HttpMethod httpMethod, Class<T> reponseType){
        // http requset 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // httpEntity 생성 = header + requestbody
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);

        // request url 설정
        String url = HOST + endPoint;

        // http request 전송
        return restTemplate.exchange(url, httpMethod, httpEntity, reponseType);
    }

    // 서비스 클래스 내부의 testMethod를 수정
    public ResponseDto1 predictCategory(RequestDto1 requestDto1) {
        try {
            // 인자로 받은 requestDto1을 사용하여 body 생성
            String body = objectMapper.writeValueAsString(requestDto1);

            ResponseEntity<ResponseDto1> responseEntity = requestToApi(
                    "/predict/",
                    body,
                    HttpMethod.POST,
                    ResponseDto1.class);

            return responseEntity.getBody();

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
