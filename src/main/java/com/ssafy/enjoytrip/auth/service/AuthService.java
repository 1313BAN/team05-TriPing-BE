package com.ssafy.enjoytrip.auth.service;

import com.ssafy.enjoytrip.auth.dto.LoginRequestDTO;

public interface AuthService {
    String login(LoginRequestDTO request);  // JWT 토큰 반환
}


// 로그인. 예외처리 등
// 여기서 passwordEncoder (bcrypt) 사용해야 함.