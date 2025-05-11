package com.ssafy.enjoytrip.domain.user.dto;

import com.ssafy.enjoytrip.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginResponseDTO {
    private boolean success;
    private User user;
}
