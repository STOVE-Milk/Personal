package com.steam.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
    Long expiration;
    String accessToken;
    String refreshToken;
}
