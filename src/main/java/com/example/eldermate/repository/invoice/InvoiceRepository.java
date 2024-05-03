package com.example.eldermate.repository.invoice;

import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.InvoiceQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>, InvoiceRepositoryCustom {
}