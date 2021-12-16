package com.steam.auth.service;

import com.steam.auth.dto.LoginRequest;
import com.steam.auth.dto.RegistRequest;
import com.steam.auth.dto.Token;
import com.steam.auth.entity.User;
import com.steam.auth.entity.UserBuilder;
import com.steam.auth.jwt.JwtUtil;
import com.steam.auth.repository.AuthRepository;
import com.steam.auth.util.HashedPassword;
import com.steam.auth.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    public Token login(LoginRequest request) {
        User user = authRepository.findUserByEmail(request.getEmail())
                .orElseThrow();

        if(!PasswordEncoder.match(user.getPassword(), user.getSalt())) {
        }

        return jwtUtil.createAccessToken(user);
    }

    public Boolean regist(RegistRequest request) {
        Optional<User> user = authRepository.findUserByEmail(request.getEmail());

        if(user.isPresent()) {
            //이미 유저가 존재합니다.
            return false;
        }

        HashedPassword password = PasswordEncoder.hash(request.getPassword());

        User newUser = User.builder()
                .email(request.getEmail())
                .password(password.getHashedPassword())
                .salt(password.getSalt())
                .nickname(request.getNickname())
                .build();

        authRepository.save(newUser);

        return true;
    }

    public Boolean isDuplicated(String email) {
        Optional<User> user = authRepository.findUserByEmail(email);

        if(user.isPresent()) {
            //이미 유저가 존재합니다.
            return true;
        }

        return false;
    }
}
