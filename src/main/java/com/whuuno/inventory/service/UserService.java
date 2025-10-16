package com.whuuno.inventory.service;

import com.whuuno.inventory.model.User;

public interface UserService {

    User registerUser(User user);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}