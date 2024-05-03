package com.example.eldermate.repository.nonPayment;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.nonPayment.NonPaymentRepositoryCustom;
import com.example.eldermate.repository.queryDto.NonPaymentQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.eldermate.entity.QNonPayment.nonPayment;
import static com.example.eldermate.entity.QMessage.message;

@RequiredArgsConstructor
public class NonPaymentRepositoryImpl implements NonPaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<NonPaymentQueryDto> findAllByUser(UserEntity User) {
        List<NonPaymentQueryDto> queryDtos = queryFactory.select(Projections.constructor(NonPaymentQueryDto.class,
                message.id,
                nonPayment.payee,
                nonPayment.cost,
                nonPayment.time
                ))
                .from(nonPayment)
                .join(nonPayment.message, message)
                .where(message.confirm.eq(false), message.user.eq(User))
                .fetch();

        return queryDtos;
    }
}
