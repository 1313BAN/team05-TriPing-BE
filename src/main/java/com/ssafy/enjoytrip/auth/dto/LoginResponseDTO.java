package com.ssafy.enjoytrip.auth.dto;

import com.ssafy.enjoytrip.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {
    private boolean success;
    private User user;
}

// accessToken 응답