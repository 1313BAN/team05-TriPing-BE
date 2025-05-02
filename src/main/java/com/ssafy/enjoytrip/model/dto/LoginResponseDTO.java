package com.ssafy.enjoytrip.model.dto;

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
    private UserDTO user;
}
