package com.example.eldermate.repository.nonPayment;

import com.example.eldermate.entity.Invoice;
import com.example.eldermate.entity.NonPayment;
import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.NonPaymentQueryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NonPaymentRepository extends JpaRepository<NonPayment, Long>, NonPaymentRepositoryCustom {
}
