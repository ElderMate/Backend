package com.example.eldermate.service;

import com.example.eldermate.dto.RequestDto1;
import com.example.eldermate.dto.ResponseDto1;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; //객체 -> Json으로 변경
    private final String HOST = "http://127.0.0.1:8000";

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

    public ResponseDto1 testMethod() {
        try {
            RequestDto1 requestDto1 = new RequestDto1("텍스트 내용");
            String body = objectMapper.writeValueAsString(requestDto1);

            ResponseEntity<ResponseDto1> responseDto1Entity = requestToApi(
                    "/predict/",
                    body,
                    HttpMethod.POST,
                    ResponseDto1.class);

            return responseDto1Entity.getBody();

        } catch (Exception e){
            throw new RuntimeException();
        }
    }




}
