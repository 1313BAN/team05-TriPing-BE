package com.ssafy.enjoytrip.auth.service;

import com.ssafy.enjoytrip.auth.dto.LoginRequestDTO;
import com.ssafy.enjoytrip.auth.dto.LoginResponseDTO;
import com.ssafy.enjoytrip.auth.exception.AuthException;
import com.ssafy.enjoytrip.auth.jwt.JwtProvider;
import com.ssafy.enjoytrip.domain.user.mapper.UserMapper;
import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.exception.ErrorCode;
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
        User user = userMapper.selectByEmail(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException(ErrorCode.INVALID_CREDENTIALS);
        }

        return jwtProvider.createToken(String.valueOf(user.getId()), List.of("ROLE_USER"));

    }
}