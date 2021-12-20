package com.steam.auth.service;

import com.steam.auth.dto.LoginRequest;
import com.steam.auth.dto.RegistRequest;
import com.steam.auth.dto.Token;
import com.steam.auth.entity.User;
import com.steam.auth.exception.custom.AuthFailedException;
import com.steam.auth.exception.custom.EmailDuplicatedException;
import com.steam.auth.jwt.JwtUtil;
import com.steam.auth.repository.AuthRepository;
import com.steam.auth.util.HashedPassword;
import com.steam.auth.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;

    public Token login(LoginRequest request) {
        User user = authRepository.findUserByEmail(request.getEmail())
                .orElseThrow(AuthFailedException::new);

        if(!PasswordEncoder.match(request.getPassword(), user.getPassword())) {
            throw new AuthFailedException();
        }

        return jwtUtil.createAccessToken(user);
    }

    public Boolean regist(RegistRequest request) {
        Optional<User> user = authRepository.findUserByEmail(request.getEmail());

        if(user.isPresent()) {
            throw new EmailDuplicatedException();
        }

        HashedPassword password = PasswordEncoder.hash(request.getPassword());

        User newUser = User.builder()
                .email(request.getEmail())
                .password(password.getHashedPassword())
                .nickname(request.getNickname())
                .build();

        authRepository.save(newUser);

        return true;
    }

    public Boolean isDuplicated(String email) {
        Optional<User> user = authRepository.findUserByEmail(email);

        return user.isPresent();
    }
}
