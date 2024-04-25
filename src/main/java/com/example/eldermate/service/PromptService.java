package com.example.eldermate.service;

import com.example.eldermate.dto.*;
import com.example.eldermate.entity.*;
import com.example.eldermate.repository.*;
import com.example.eldermate.repository.queryDto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final MessageRepository messageRepository;
    private final AutoTransferRepository autoTransferRepository;
    private final CancelRepository cancelRepository;
    private final ConfirmRepository confirmRepository;
    private final InvoiceRepository invoiceRepository;
    private final NonPaymentRepository nonPaymentRepository;
    private final OpenRepository openRepository;
    private final RejectRepository repository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String API_URL = "http://127.0.0.1:8000";


    public PromptStartResponseDto startPrompt(UserEntity user){
        //1. 파일 이름 생성
        String fileName = createFileName(user.getName());

        //2. DB에서 confirm == not인 문자들 가져오기
        List<AutoTransferQueryDto> autoTransfers = autoTransferRepository.findAllByUser(user);
        List<CancelQueryDto> cancels = cancelRepository.findAllByUser(user);
        List<ConfirmQueryDto> confirms = confirmRepository.findAllByUser(user);
        List<InvoiceQueryDto> invoices = invoiceRepository.findAllByUser(user);
        List<NonPaymentQueryDto> nonPayments = nonPaymentRepository.findAllByUser(user);
        List<OpenQueryDto> opens = openRepository.findAllByUser(user);
        List<RejectQueryDto> rejects = repository.findAllByUser(user);

        //3. 가져온 문자들 confirm == true로 설정하기
        // messageId를 모으기 위한 리스트 생성
        List<Long> messageIds = new ArrayList<>();

        // 각 리스트에서 messageId를 추출하여 messageIds에 추가
        autoTransfers.forEach(dto -> messageIds.add(dto.messageId()));
        cancels.forEach(dto -> messageIds.add(dto.messageId()));
        confirms.forEach(dto -> messageIds.add(dto.messageId()));
        invoices.forEach(dto -> messageIds.add(dto.messageId()));
        nonPayments.forEach(dto -> messageIds.add(dto.messageId()));
        opens.forEach(dto -> messageIds.add(dto.messageId()));
        rejects.forEach(dto -> messageIds.add(dto.messageId()));

        List<Message> messages = messageRepository.findAllByIds(messageIds);

        messages.forEach(Message::setConfirm);

        //4. 해당 문자 정보들 FastAPI 저달
        PromptStartRequestDto requestDto = new PromptStartRequestDto(
                fileName,
                autoTransfers,
                cancels,
                confirms,
                invoices,
                nonPayments,
                opens,
                rejects
        );

        String body = makeRequestBody(requestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<PromptResponseDto> response = restTemplate.exchange(
                    API_URL + "/reports/start",
                    HttpMethod.POST,
                    entity,
                    PromptResponseDto.class
            );

            return PromptStartResponseDto.from(response.getBody(), fileName);

        } catch (Exception e) {
            throw new RuntimeException("외부API 요청 실패");
        }

    }

    public void endPrompt(UserEntity user, PromptEndRequestDto requestDto){
        try {
            String body =  objectMapper.writeValueAsString(requestDto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<PromptEndResponseDto> response = restTemplate.exchange(
                    API_URL + "/reports/end",
                    HttpMethod.POST,
                    entity,
                    PromptEndResponseDto.class
            );

            List<Long> messageIds = response.getBody().messageIds();
            List<String> reasons = response.getBody().reasons();

            List<Message> messages = messageRepository.findAllByIds(messageIds);

            // messages 리스트를 순회하면서 각 Message에 문제 상태와 이유를 설정
            for (int i = 0; i < messages.size(); i++) {
                Message message = messages.get(i);
                message.setIsProblem(); // 문제 상태를 true로 설정
                // 동일한 인덱스를 가진 reasons 목록에서 문제의 이유를 가져와 설정
                if (i < reasons.size()) { // reasons 목록의 크기를 넘지 않도록 체크
                    message.setProblemReason(reasons.get(i));
                }
            }


        } catch (Exception e) {
            throw new RuntimeException("외부API 요청 실패");
        }
    }

    private String createFileName(String name){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateInfo = dateFormat.format(new Date());
        return name + "_" + dateInfo;
    }

    private String makeRequestBody(PromptStartRequestDto requestDto){
        try {
            return objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("requset body 생성 실패");
        }
    }

}
