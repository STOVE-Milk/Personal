package com.steam.auth.repository;

import com.steam.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserById(Integer id);
}
