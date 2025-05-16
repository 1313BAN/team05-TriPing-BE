package com.ssafy.enjoytrip.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;
}