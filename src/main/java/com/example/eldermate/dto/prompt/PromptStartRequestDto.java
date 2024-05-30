package com.example.eldermate.dto.prompt;

import java.util.List;

public record PromptStartRequestDto(
        String fileName,
        List<AutoTransferDto> autoTransfers,
        List<CancelDto> cancels,
        List<ConfirmDto> confirms,
        List<InvoiceDto> invoices,
        List<OpenDto> opens
) {
}
