package com.example.eldermate.repository.reject;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.RejectQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.eldermate.entity.QMessage.message;
import static com.example.eldermate.entity.QReject.reject;

@RequiredArgsConstructor
public class RejectRepositoryImpl implements RejectRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<RejectQueryDto> findAllByUser(UserEntity User) {
        List<RejectQueryDto> queryDtos = queryFactory.select(Projections.constructor(RejectQueryDto.class,
                message.id,
                reject.method,
                reject.location,
                reject.time,
                reject.cost,
                reject.rejectReason
                ))
                .from(reject)
                .join(reject.message, message)
                .where(message.confirm.eq(false), message.user.eq(User))
                .fetch();

        return queryDtos;
    }
}
