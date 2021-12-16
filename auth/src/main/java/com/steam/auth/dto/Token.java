package com.steam.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Token {
    String accessToken;
    String refreshToken;
}
