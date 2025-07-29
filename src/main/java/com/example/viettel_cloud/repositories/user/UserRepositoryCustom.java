package com.example.viettel_cloud.repositories.user;

import com.example.viettel_cloud.entities.User;

public interface UserRepositoryCustom {
    User getUserByIssAndSub(String iss, String sub);
}
