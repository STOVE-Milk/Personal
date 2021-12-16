package com.steam.auth.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    public static HashedPassword hash(String password) {
        String salt = BCrypt.gensalt();
        return HashedPassword.builder()
                .hashedPassword(BCrypt.hashpw(password, salt))
                .salt(salt)
                .build();
    }

    public static Boolean match(String password, String salt) {
        return BCrypt.checkpw(password, salt);
    }
}
