package com.xiaoxili.userservice.service;

import com.xiaoxili.userservice.model.User;

public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
