package com.example.eldermate.service;

import com.example.eldermate.entity.*;
import com.example.eldermate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {
    private final AutoTransferRepository autoTransferRepository;
    private final CancelRepository cancelRepository;
    private final ConfirmRepository confirmRepository;
    private final InvoiceRepository invoiceRepository;
    private final NonPaymentRepository nonPaymentRepository;
    private final OpenRepository openRepository;
    private final RejectRepository repository;


    public String getFirstPrompt(UserEntity user){
        List<AutoTransfer> autoTransfers = autoTransferRepository.findAllByUser(user);
        List<Cancel> cancels = cancelRepository.findAllByUser(user);
        List<Confirm> confirms = confirmRepository.findAllByUser(user);
        List<Invoice> invoices = invoiceRepository.findAllByUser(user);
        List<NonPayment> nonPayments = nonPaymentRepository.findAllByUser(user);
        List<Open> opens = openRepository.findAllByUser(user);
        List<Reject> rejects = repository.findAllByUser(user);
        // 이후 추가로 필요한 작업 수행

        StringBuilder promptBuilder = new StringBuilder();

        if(!confirms.isEmpty()) {
            promptBuilder.append("결제 승인 문자들:").append("\n");
            confirms.forEach(cf -> promptBuilder.append(cf.toString()).append("\n"));
        }
        if(!cancels.isEmpty()) {
            promptBuilder.append("결제 취소 문자들:").append("\n");
            cancels.forEach(c -> promptBuilder.append(c.toString()).append("\n"));
        }
        if(!confirms.isEmpty()) {
            promptBuilder.append("결제 거절 문자들:").append("\n");
            rejects.forEach(rj -> promptBuilder.append(rj.toString()).append("\n"));
        }
        if(!invoices.isEmpty()) {
            promptBuilder.append("납부 예정 문자들:").append("\n");
            invoices.forEach(iv -> promptBuilder.append(iv.toString()).append("\n"));
        }
        if(!nonPayments.isEmpty()) {
            promptBuilder.append("미납 문자들:").append("\n");
            nonPayments.forEach(np -> promptBuilder.append(np.toString()).append("\n"));
        }
        if(!autoTransfers.isEmpty()) {
            promptBuilder.append("자동이체 등록 문자들:").append("\n");
            autoTransfers.forEach(at -> promptBuilder.append(at.toString()).append("\n"));
        }
        if(!opens.isEmpty()) {
            promptBuilder.append("계좌 개설 문자들:").append("\n");
            opens.forEach(op -> promptBuilder.append(op.toString()).append("\n"));
        }

        return promptBuilder.toString();
    }
}
