package com.studytracker.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.studytracker.entity.User;
import com.studytracker.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User register(String email, String rawPassword) {
        String hashed = encoder.encode(rawPassword);
        return userRepository.save(new User(email, hashed));
    }

    public boolean login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;
    
        return encoder.matches(rawPassword, user.getPassword());
    }
}
