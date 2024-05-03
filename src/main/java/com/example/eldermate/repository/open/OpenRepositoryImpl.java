package com.example.eldermate.repository.open;

import com.example.eldermate.entity.UserEntity;
import com.example.eldermate.repository.queryDto.OpenQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.eldermate.entity.QMessage.message;
import static com.example.eldermate.entity.QOpen.open;

@RequiredArgsConstructor
public class OpenRepositoryImpl implements OpenRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<OpenQueryDto> findAllByUser(UserEntity User) {
        List<OpenQueryDto> queryDtos = queryFactory.select(Projections.constructor(OpenQueryDto.class,
                message.id,
                open.bank,
                open.type
                ))
                .from(open)
                .join(open.message, message)
                .where(message.confirm.eq(false), message.user.eq(User))
                .fetch();

        return queryDtos;
    }
}
