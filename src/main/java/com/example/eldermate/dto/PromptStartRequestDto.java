package com.example.eldermate.dto;

import com.example.eldermate.repository.queryDto.*;

import java.util.List;

public record PromptStartRequestDto(
        String fileName,
        List<AutoTransferQueryDto> autoTransfers,
        List<CancelQueryDto> cancels,
        List<ConfirmQueryDto> confirms,
        List<InvoiceQueryDto> invoices,
        List<NonPaymentQueryDto> nonPayments,
        List<OpenQueryDto> opens,
        List<RejectQueryDto> rejects
) {
}
