package com.studytracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studytracker.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
