package com.example.eldermate.repository.autoTransfer;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.AutoTransferQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.eldermate.entity.QAutoTransfer.autoTransfer;
import static com.example.eldermate.entity.QMessage.message;

@RequiredArgsConstructor
public class AutoTransferRepositoryImpl implements AutoTransferRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<AutoTransferQueryDto> findAllByUser(UserEntity User) {
        List<AutoTransferQueryDto> queryDtos = queryFactory.select(Projections.constructor(AutoTransferQueryDto.class,
                message.id,
                autoTransfer.bank,
                autoTransfer.company
                ))
                .from(autoTransfer)
                .join(autoTransfer.message, message)
                .where(message.confirm.eq(false), message.user.eq(User))
                .fetch();

        return queryDtos;
    }
}
