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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
        List<Message> messages = messageRepository.findNotConfirmAllByUser(user);

        log.info(messages.toString());

        //3. 엔티티를 DTO로 매핑
        List<AutoTransferDto> autoTransferDtos = new ArrayList<>();
        List<CancelDto> cancelDtos = new ArrayList<>();
        List<ConfirmDto> confirmDtos = new ArrayList<>();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        List<OpenDto> openDtos = new ArrayList<>();

        for (Message message : messages) {
            if (message instanceof AutoTransfer) {
                AutoTransfer autoTransfer = (AutoTransfer) message;
                autoTransferDtos.add(new AutoTransferDto(autoTransfer.getId(), autoTransfer.getBank(), autoTransfer.getCompany()));
            } else if (message instanceof Cancel) {
                Cancel cancel = (Cancel) message;
                cancelDtos.add(new CancelDto(cancel.getId(), cancel.getMethod(), cancel.getLocation(), cancel.getCancelTime(), cancel.getCost()));
            } else if (message instanceof Confirm) {
                Confirm confirm = (Confirm) message;
                confirmDtos.add(new ConfirmDto(confirm.getId(), confirm.getMethod(), confirm.getLocation(), confirm.getConfirmTime(), confirm.getCost()));
            } else if (message instanceof Invoice) {
                Invoice invoice = (Invoice) message;
                invoiceDtos.add(new InvoiceDto(invoice.getId(), invoice.getPayee(), invoice.getCost(), invoice.getInvoiceTime(), invoice.getPaymentReason()));
            } else if (message instanceof Open) {
                Open open = (Open) message;
                openDtos.add(new OpenDto(open.getId(), open.getBank(), open.getType()));
            }
        }

        //4. 가져온 문자들 confirm == true로 설정하기
        messages.forEach(Message::setConfirm);

        //5. 해당 문자 정보들 FastAPI 전달
        PromptStartRequestDto requestDto = new PromptStartRequestDto(
                fileName,
                autoTransferDtos,
                cancelDtos,
                confirmDtos,
                invoiceDtos,
                openDtos
        );

        log.info(requestDto.toString());

        String body;
        try {
            body = objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("request body 생성 실패");
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
            log.info(messages.toString());
            // messages 리스트를 순회하면서 각 Message에 문제 상태와 이유를 설정
            for (int i = 0; i < messages.size(); i++) {
                Message message = messages.get(i);
                log.info(message.toString());
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
