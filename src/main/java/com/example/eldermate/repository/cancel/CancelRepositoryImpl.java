package com.example.eldermate.repository.cancel;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.AutoTransferQueryDto;
import com.example.eldermate.repository.queryDto.CancelQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.eldermate.entity.QAutoTransfer.autoTransfer;
import static com.example.eldermate.entity.QCancel.cancel;
import static com.example.eldermate.entity.QMessage.message;

@RequiredArgsConstructor
public class CancelRepositoryImpl implements CancelRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<CancelQueryDto> findAllByUser(UserEntity User) {
        List<CancelQueryDto> queryDtos = queryFactory.select(Projections.constructor(CancelQueryDto.class,
                message.id,
                cancel.method,
                cancel.location,
                cancel.time,
                cancel.cost
                ))
                .from(cancel)
                .join(cancel.message, message)
                .where(message.confirm.eq(false), message.user.eq(User))
                .fetch();

        return queryDtos;
    }
}
