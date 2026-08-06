package com.sentinelvault.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sentinelvault.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}