package com.example.eldermate.repository.autoTransfer;

import com.example.eldermate.entity.AutoTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoTransferRepository extends JpaRepository<AutoTransfer, Long>, AutoTransferRepositoryCustom {
}
