package com.steam.management.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Builder
@Data
public class UserPageResponse {
    Integer pages;
    List<UserDto> users;
}
