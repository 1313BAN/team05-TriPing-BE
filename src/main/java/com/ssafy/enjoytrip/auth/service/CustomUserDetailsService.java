// 1. UserDetailsService 구현체 생성
package com.ssafy.enjoytrip.auth.service;

import com.ssafy.enjoytrip.auth.jwt.UserPrincipal;
import com.ssafy.enjoytrip.domain.user.mapper.UserMapper;
import com.ssafy.enjoytrip.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        return UserPrincipal.from(user);
    }

    // JWT 인증을 위한 메서드 추가
    public UserDetails loadUserById(Long userId) {
        User user = userMapper.selectUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("사용자 ID를 찾을 수 없습니다: " + userId);
        }
        return UserPrincipal.from(user);
    }
}