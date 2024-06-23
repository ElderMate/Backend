package com.example.eldermate.service;

import com.example.eldermate.dto.*;
import com.example.eldermate.dto.message.*;
import com.example.eldermate.entity.*;
import com.example.eldermate.repository.*;
import com.example.eldermate.repository.AutoTransferRepository;
import com.example.eldermate.repository.CancelRepository;
import com.example.eldermate.repository.ConfirmRepository;
import com.example.eldermate.repository.InvoiceRepository;
import com.example.eldermate.repository.OpenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    private final ConfirmRepository confirmRepository;
    private final CancelRepository cancelRepository;
    private final OpenRepository openRepository;
    private final InvoiceRepository invoiceRepository;
    private final AutoTransferRepository autoTransferRepository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; //객체 -> Json으로 변경

    @Value("${service.host}")
    private String HOST;

    public Slice<MessageResponseDto> getAllMessage(CustomUserDetails userDetails, int pageNo, int size){
        UserEntity user = userDetails.getUserEntity();

        PageRequest limit = PageRequest.of(pageNo, size);

        Slice<MessageResponseDto> messages = messageRepository.findAllDTOByUser(user, limit);

        return messages;
    }

    public void saveMessage(MessageDTO messageDTO, CustomUserDetails userDetails) {
        UserEntity user = userDetails.getUserEntity();
        log.info("MessageService user check : {} ", user.toString());
        log.info("Received MessageDTO : {}", messageDTO.toString());

        // DTO에서 받은 time 문자열을 LocalDateTime으로 변환
        //LocalDateTime date = LocalDateTime.parse(messageDTO.getTime(), DateTimeFormatter.ISO_DATE);

        // 외부 API로부터 category 예측 받기
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(messageDTO.getMsg()); // msg를 기반으로 DTO 생성
        CategoryResponseDto categoryResponseDto = predictCategory(categoryRequestDto); // category 예측 받기

        log.info("classified Message : {} ", categoryResponseDto.response());

        handleCategoryResponse(messageDTO, categoryResponseDto.response(), user);
    }

    // 서비스 클래스 내부의 testMethod를 수정
    public CategoryResponseDto predictCategory(CategoryRequestDto categoryRequestDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = objectMapper.writeValueAsString(categoryRequestDto);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<CategoryResponseDto> responseEntity = restTemplate.exchange(
                    HOST + "/class/",
                    HttpMethod.POST,
                    entity,
                    CategoryResponseDto.class);

            return responseEntity.getBody();

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void handleCategoryResponse(MessageDTO messageDTO, String category, UserEntity user) {
        switch (category) {

            case "결제 승인":
                processConfirmCategory(messageDTO, user);
                break;
            case "결제 취소":
                processCancelCategory(messageDTO, user);
                break;
            case "계좌 개설":
                processOpenCategory(messageDTO, user);
                break;
            case "납부 예정":
                processInvoiceCategory(messageDTO, user);
                break;
            case "자동 이체":
                processAutoCategory(messageDTO, user);
                break;

            default:
                log.info("No special processing for category: {}", category);
        }
    }


    private void processConfirmCategory(MessageDTO messageDTO,  UserEntity user) {
        try {
            NerRequestDto nerRequestDto = new NerRequestDto(messageDTO.getMsg());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = objectMapper.writeValueAsString(nerRequestDto);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<ConfirmResponseDto> responseEntity = restTemplate.exchange(
                    HOST + "/keywords/payment_approval",
                    HttpMethod.POST,
                    entity,
                    ConfirmResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                ConfirmResponseDto responseDto = responseEntity.getBody();

                Confirm confirm = Confirm.builder()
                        .pNum(messageDTO.getPNum())
                        .msg(messageDTO.getMsg())
                        .receiveTime(messageDTO.getTime())
                        .user(user)
                        .method(responseDto.METHOD())
                        .location(responseDto.LOCATION())
                        .confirmTime(responseDto.TIME())
                        .cost(responseDto.COST())
                        .build();

                confirmRepository.save(confirm);
            } else {
                log.error("Failed to get a successful response for category '결제 승인'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }
    }

    private void processCancelCategory(MessageDTO messageDTO,  UserEntity user) {
        try {
            NerRequestDto nerRequestDto = new NerRequestDto(messageDTO.getMsg());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = objectMapper.writeValueAsString(nerRequestDto);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<CancelResponseDto> responseEntity = restTemplate.exchange(
                    HOST + "/keywords/payment_cancellation",
                    HttpMethod.POST,
                    entity,
                    CancelResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                CancelResponseDto responseDto = responseEntity.getBody();

                Cancel cancel = Cancel.builder()
                        .pNum(messageDTO.getPNum())
                        .msg(messageDTO.getMsg())
                        .receiveTime(messageDTO.getTime())
                        .user(user)
                        .method(responseDto.METHOD())
                        .location(responseDto.LOCATION())
                        .cancelTime(responseDto.TIME())
                        .cost(responseDto.COST())
                        .build();

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

    private void processOpenCategory(MessageDTO messageDTO,  UserEntity user) {
        try {
            NerRequestDto nerRequestDto = new NerRequestDto(messageDTO.getMsg());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = objectMapper.writeValueAsString(nerRequestDto);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<OpenResponseDto> responseEntity = restTemplate.exchange(
                    HOST +  "/keywords/account_opening",
                    HttpMethod.POST,
                    entity,
                    OpenResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                OpenResponseDto responseDto = responseEntity.getBody();
                Open open = Open.builder()
                        .pNum(messageDTO.getPNum())
                        .msg(messageDTO.getMsg())
                        .receiveTime(messageDTO.getTime())
                        .user(user)
                        .bank(responseDto.BANK())
                        .type(responseDto.TYPE())
                        .build();

                openRepository.save(open);
            } else {
                log.error("Failed to get a successful response for category '계좌 개설'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }
    }

    private void processInvoiceCategory(MessageDTO messageDTO,  UserEntity user) {
        try {
            NerRequestDto nerRequestDto = new NerRequestDto(messageDTO.getMsg());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = objectMapper.writeValueAsString(nerRequestDto);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<InvoiceResponseDto> responseEntity = restTemplate.exchange(
                    HOST + "/keywords/payment_scheduled",
                    HttpMethod.POST,
                    entity,
                    InvoiceResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                InvoiceResponseDto responseDto = responseEntity.getBody();
                Invoice invoice = Invoice.builder()
                        .pNum(messageDTO.getPNum())
                        .msg(messageDTO.getMsg())
                        .receiveTime(messageDTO.getTime())
                        .user(user)
                        .payee(responseDto.PAYEE())
                        .cost(responseDto.COST())
                        .invoiceTime(responseDto.TIME())
                        .paymentReason(responseDto.PAYMENTREASON())
                        .build();

                invoiceRepository.save(invoice);
            } else {
                log.error("Failed to get a successful response for category '납부 예정'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }

    }

    private void processAutoCategory(MessageDTO messageDTO,  UserEntity user) {
        try {
            NerRequestDto nerRequestDto = new NerRequestDto(messageDTO.getMsg());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = objectMapper.writeValueAsString(nerRequestDto);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<AutoTransferResponseDto> responseEntity = restTemplate.exchange(
                    HOST + "/keywords/automatic_transfer",
                    HttpMethod.POST,
                    entity,
                    AutoTransferResponseDto.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                AutoTransferResponseDto responseDto = responseEntity.getBody();

                AutoTransfer autoTransfer = AutoTransfer.builder()
                        .pNum(messageDTO.getPNum())
                        .msg(messageDTO.getMsg())
                        .receiveTime(messageDTO.getTime())
                        .user(user)
                        .bank(responseDto.BANK())
                        .company(responseDto.COMPANY())
                        .build();

                autoTransferRepository.save(autoTransfer);
            } else {
                log.error("Failed to get a successful response for category '자동 이체'");
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing requestDto2 to JSON", e);
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
        }

    }
}
