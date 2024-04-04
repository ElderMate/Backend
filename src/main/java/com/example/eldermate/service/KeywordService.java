package com.example.eldermate.service;

import com.example.eldermate.dto.*;
import com.example.eldermate.entity.*;
import com.example.eldermate.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class KeywordService {

    private final CancelRepository cancelRepository;
    private final RejectRepository rejectRepository;
    private final OpenRepository openRepository;
    private final InvoiceRepository invoiceRepository;
    private final NonPaymentRepository nonPaymentRepository;

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

    public void handleCategoryResponse(Message message, String category) {
        switch (category) {

            case "결제 거절":
                processRejectCategory(message);
                break;
            case "결제 승인", "결제 취소":
                processConfirmCancelCategory(message);
                break;
            case "계좌 개설":
                processOpenCategory(message);
                break;
            case "납부 예정":
                processInvoiceCategory(message);
                break;
            case "미납":
                processNonPayCategory(message);
                break;
            case "자동 이체":
                processAutoCategory(message);
                break;

            default:
                // 기본 처리 또는 로깅
                log.info("No special processing for category: {}", category);
        }
    }

    private void processRejectCategory(Message message) {
        try {
            RequestDto2 requestDto2 = new RequestDto2(message.getMsg());
            String requestBody = objectMapper.writeValueAsString(requestDto2);

            ResponseEntity<RejectResponseDto> responseEntity = requestToApi(
                    "/keywords/payment_denial",
                    requestBody,
                    HttpMethod.POST,
                    RejectResponseDto.class);
            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                RejectResponseDto responseDto = responseEntity.getBody();
                Reject reject = new Reject();
                reject.setMethod(responseDto.METHOD());
                reject.setLocation(responseDto.LOCATION());
                reject.setTime(responseDto.TIME());
                reject.setCost(responseDto.COST());
                reject.setRejectReason(responseDto.RejectionReason());
                reject.setMessage(message);
                rejectRepository.save(reject);
            } else {
                log.error("Failed to get a successful response for category '결제 취소'");
            }
        } catch (JsonProcessingException e){
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }

    }

    private void processConfirmCancelCategory(Message message) {
        try {
            RequestDto2 requestDto2 = new RequestDto2(message.getMsg());
            String requestBody = objectMapper.writeValueAsString(requestDto2);

            ResponseEntity<CancelConfirmResponseDto> responseEntity = requestToApi(
                    "/keywords/payment_cancellation",
                    requestBody,
                    HttpMethod.POST,
                    CancelConfirmResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                CancelConfirmResponseDto responseDto = responseEntity.getBody();
                Cancel cancel = new Cancel();
                cancel.setMethod(responseDto.METHOD());
                cancel.setLocation(responseDto.LOCATION());
                cancel.setTime(responseDto.TIME());
                cancel.setCost(responseDto.COST());
                cancel.setMessage(message); // Message 엔티티와 연관
                cancelRepository.save(cancel);
            } else {
                log.error("Failed to get a successful response for category '결제 취소'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }
    }

    private void processOpenCategory(Message message) {
        try {
            RequestDto2 requestDto2 = new RequestDto2(message.getMsg());
            String requestBody = objectMapper.writeValueAsString(requestDto2);

            ResponseEntity<OpenResponseDto> responseEntity = requestToApi(
                    "/keywords/account_openning",
                    requestBody,
                    HttpMethod.POST,
                    OpenResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                OpenResponseDto responseDto = responseEntity.getBody();
                Open open = new Open();
                open.setBank(responseDto.BANK());
                open.setType(responseDto.TYPE());
                open.setMessage(message); // Message 엔티티와 연관
                openRepository.save(open);
            } else {
                log.error("Failed to get a successful response for category '결제 취소'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }
    }

    private void processInvoiceCategory(Message message) {
        try {
            RequestDto2 requestDto2 = new RequestDto2(message.getMsg());
            String requestBody = objectMapper.writeValueAsString(requestDto2);

            ResponseEntity<InvoiceResponseDto> responseEntity = requestToApi(
                    "/keywords/payment_scheduled",
                    requestBody,
                    HttpMethod.POST,
                    InvoiceResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                InvoiceResponseDto responseDto = responseEntity.getBody();
                Invoice invoice = new Invoice();
                invoice.setPayee(responseDto.PAYEE());
                invoice.setCost(responseDto.COST());
                invoice.setTime(responseDto.TIME());
                invoice.setPaymentReason(responseDto.PAYMENTREASON());
                invoice.setMessage(message); // Message 엔티티와 연관
                invoiceRepository.save(invoice);
            } else {
                log.error("Failed to get a successful response for category '결제 취소'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }

    }

    private void processNonPayCategory(Message message) {
        try {
            RequestDto2 requestDto2 = new RequestDto2(message.getMsg());
            String requestBody = objectMapper.writeValueAsString(requestDto2);

            ResponseEntity<NonPaymentResponseDto> responseEntity = requestToApi(
                    "/keywords/non_payment",
                    requestBody,
                    HttpMethod.POST,
                    NonPaymentResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                NonPaymentResponseDto responseDto = responseEntity.getBody();
                NonPayment nonPayment = new NonPayment();
                nonPayment.setPayee(responseDto.PAYEE());
                nonPayment.setCost(responseDto.COST());
                nonPayment.setTime(responseDto.TIME());
                nonPayment.setMessage(message); // Message 엔티티와 연관
                nonPaymentRepository.save(nonPayment);
            } else {
                log.error("Failed to get a successful response for category '결제 취소'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }
    }

    private void processAutoCategory(Message message) {

    }
}
