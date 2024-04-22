package com.example.eldermate.repository.queryDto;

import com.example.eldermate.entity.Message;
import jakarta.persistence.*;

public record AutoTransferQueryDto(
        Long messageId,
        String bank,
        String company

) { }
