package com.steam.management.dto;

import com.steam.management.entity.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    Integer id;
    String email;
    String nickname;
    String role;
    String verified;

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole() ? "SPECIAL" : "NORMAL")
                .verified(user.getVerified() ? "인증 완료" : "인증 대기")
                .build();
    }
}
