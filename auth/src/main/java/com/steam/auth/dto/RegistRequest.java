package com.steam.auth.dto;

import lombok.Data;

@Data
public class RegistRequest {
    String email;
    String password;
    String nickname;
}
