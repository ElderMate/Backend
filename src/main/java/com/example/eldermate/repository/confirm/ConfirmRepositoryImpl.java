package com.example.eldermate.repository.confirm;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.ConfirmQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.eldermate.entity.QConfirm.confirm;
import static com.example.eldermate.entity.QMessage.message;

@RequiredArgsConstructor
public class ConfirmRepositoryImpl implements ConfirmRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<ConfirmQueryDto> findAllByUser(UserEntity User) {
        List<ConfirmQueryDto> queryDtos = queryFactory.select(Projections.constructor(ConfirmQueryDto.class,
                message.id,
                confirm.method,
                confirm.location,
                confirm.time,
                confirm.cost
                ))
                .from(confirm)
                .join(confirm.message, message)
                .where(message.confirm.eq(false), message.user.eq(User))
                .fetch();

        return queryDtos;
    }
}
