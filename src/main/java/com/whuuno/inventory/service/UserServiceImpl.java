package com.whuuno.inventory.service;

import com.whuuno.inventory.dao.UserDAO;
import com.whuuno.inventory.exception.ResourceNotFoundException;
import com.whuuno.inventory.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDAO.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }
}