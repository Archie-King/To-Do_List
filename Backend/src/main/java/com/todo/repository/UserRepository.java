package com.todo.repository;

import com.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by username, using for validate while log in
    Optional<User> findByUsername(String username);

    // Check if username exist
    boolean existsByUsername(String username);

    // Check if email exist
    boolean existsByEmail(String email);
}
