package com.example.eldermate.service;

import com.example.eldermate.dto.ProblemRequestDto;
import com.example.eldermate.dto.PromptResponseDto;
import com.example.eldermate.dto.PromptStartResponseDto;
import com.example.eldermate.dto.PromptStartRequestDto;
import com.example.eldermate.entity.*;
import com.example.eldermate.repository.*;
import com.example.eldermate.repository.queryDto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.text.SimpleDateFormat;
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
        String fileName = createFileName(user.getName());
        List<AutoTransferQueryDto> autoTransfers = autoTransferRepository.findAllByUser(user);
        List<CancelQueryDto> cancels = cancelRepository.findAllByUser(user);
        List<ConfirmQueryDto> confirms = confirmRepository.findAllByUser(user);
        List<InvoiceQueryDto> invoices = invoiceRepository.findAllByUser(user);
        List<NonPaymentQueryDto> nonPayments = nonPaymentRepository.findAllByUser(user);
        List<OpenQueryDto> opens = openRepository.findAllByUser(user);
        List<RejectQueryDto> rejects = repository.findAllByUser(user);

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

    @Transactional
    public void updateProblem(ProblemRequestDto dto){
        List<Long> messageIds = dto.messageIds();

        List<Message> messages = messageRepository.findAllByIds(messageIds);

        messages.forEach(Message::setIsProblem);
    }

    @Transactional
    public void updateConfime(List<Long> messageIds){
        List<Message> messages = messageRepository.findAllByIds(messageIds);

        messages.forEach(Message::setIsProblem);
    }
}
