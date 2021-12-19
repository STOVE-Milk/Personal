package com.steam.auth.util;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HashedPassword {
    String hashedPassword;
    String salt;
}
