package com.example.viettel_cloud.repositories.user;

import com.example.viettel_cloud.entities.QUser;
import com.example.viettel_cloud.entities.User;
import com.example.viettel_cloud.repositories.BaseRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryCustom {
    @Override
    public User getUserByIssAndSub(String iss, String sub) {
        QUser qUser = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qUser.iss.eq(iss));
        builder.and(qUser.sub.eq(sub));
        builder.and(qUser.deleted.eq(false));

        return query().from(qUser)
                .where(builder)
                .select(qUser)
                .fetchOne();
    }
}
