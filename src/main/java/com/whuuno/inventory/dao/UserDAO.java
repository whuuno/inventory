package com.whuuno.inventory.dao;

import com.whuuno.inventory.model.User;
import java.util.Optional;

public interface UserDAO {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}