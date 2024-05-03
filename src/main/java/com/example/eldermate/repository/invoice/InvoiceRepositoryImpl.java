package com.example.eldermate.repository.invoice;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.invoice.InvoiceRepositoryCustom;
import com.example.eldermate.repository.queryDto.InvoiceQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.eldermate.entity.QInvoice.invoice;
import static com.example.eldermate.entity.QMessage.message;

@RequiredArgsConstructor
public class InvoiceRepositoryImpl implements InvoiceRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<InvoiceQueryDto> findAllByUser(UserEntity User) {
        List<InvoiceQueryDto> queryDtos = queryFactory.select(Projections.constructor(InvoiceQueryDto.class,
                message.id,
                invoice.payee,
                invoice.cost,
                invoice.time,
                invoice.paymentReason
                ))
                .from(invoice)
                .join(invoice.message, message)
                .where(message.confirm.eq(false), message.user.eq(User))
                .fetch();

        return queryDtos;
    }
}
