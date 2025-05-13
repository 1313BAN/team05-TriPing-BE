package com.ssafy.enjoytrip.auth.service;

import com.ssafy.enjoytrip.auth.dto.LoginRequestDTO;
import com.ssafy.enjoytrip.auth.dto.LoginResponseDTO;
import com.ssafy.enjoytrip.auth.jwt.JwtProvider;
import com.ssafy.enjoytrip.domain.user.mapper.UserMapper;
import com.ssafy.enjoytrip.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String login(LoginRequestDTO request) {
        User user = userMapper.selectByEmail(request.getUserEmail());

        if (user == null || !passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())) {
            throw new RuntimeException("이메일 또는 비밀번호가 틀렸습니다.");
        }

        return jwtProvider.createToken(user.getUserEmail(), List.of("ROLE_USER"));
    }
}