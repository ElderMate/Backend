package com.example.eldermate.service;

import com.example.eldermate.dto.prompt.*;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final MessageRepository messageRepository;
    private final AutoTransferRepository autoTransferRepository;
    private final CancelRepository cancelRepository;
    private final ConfirmRepository confirmRepository;
    private final InvoiceRepository invoiceRepository;
    private final OpenRepository openRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${service.host}")
    private String API_URL;

    public PromptStartResponseDto startPrompt(UserEntity user){
        //1. 파일 이름 생성
        String fileName = createFileName(user.getUsername());

        //2. DB에서 confirm == not인 문자들 가져오기
        List<AutoTransfer> autoTransfers = autoTransferRepository.findNotConfirmAllByUser(user);
        List<Cancel> cancels = cancelRepository.findNotConfirmAllByUser(user);
        List<Confirm> confirms = confirmRepository.findNotConfirmAllByUser(user);
        List<Invoice> invoices = invoiceRepository.findNotConfirmAllByUser(user);
        List<Open> opens = openRepository.findNotConfirmAllByUser(user);

        //3. 엔티티를 DTO로 매핑
        List<AutoTransferDto> autoTransferDtos = autoTransfers.stream()
                .map(at -> new AutoTransferDto(at.getId(), at.getBank(), at.getCompany()))
                .collect(Collectors.toList());

        List<CancelDto> cancelDtos = cancels.stream()
                .map(c -> new CancelDto(c.getId(), c.getMethod(), c.getLocation(), c.getCancelTime(), c.getCost()))
                .collect(Collectors.toList());

        List<ConfirmDto> confirmDtos = confirms.stream()
                .map(cf -> new ConfirmDto(cf.getId(), cf.getMethod(), cf.getLocation(), cf.getConfirmTime(), cf.getCost()))
                .collect(Collectors.toList());

        List<InvoiceDto> invoiceDtos = invoices.stream()
                .map(iv -> new InvoiceDto(iv.getId(), iv.getPayee(), iv.getCost(), iv.getInvoiceTime(), iv.getPaymentReason()))
                .collect(Collectors.toList());

        List<OpenDto> openDtos = opens.stream()
                .map(op -> new OpenDto(op.getId(), op.getBank(), op.getType()))
                .collect(Collectors.toList());

        //4. 가져온 문자들 confirm == true로 설정하기
        autoTransfers.forEach(Message::setConfirm);
        cancels.forEach(Message::setConfirm);
        confirms.forEach(Message::setConfirm);
        invoices.forEach(Message::setConfirm);
        opens.forEach(Message::setConfirm);

        //5. 해당 문자 정보들 FastAPI 저달
        PromptStartRequestDto requestDto = new PromptStartRequestDto(
                fileName,
                autoTransferDtos,
                cancelDtos,
                confirmDtos,
                invoiceDtos,
                openDtos
        );

        String body;
        try {
            body = objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("requset body 생성 실패");
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<PromptResponseDto> response = restTemplate.exchange(
                    API_URL + "/reports/start",
                    HttpMethod.POST,
                    entity,
                    PromptResponseDto.class
            );

            return PromptStartResponseDto.from(response.getBody(), fileName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("외부API 요청 실패");
        }

    }

    public void endPrompt(UserEntity user, String fileName){
        String body;
        try {
            body = objectMapper.writeValueAsString(new PromptEndRequestDto(fileName));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("requset body 생성 실패");
        }

        try {
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
                message.setProblemReason(reasons.get(i));
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

}
