package com.ssafy.enjoytrip.auth.jwt;


import com.ssafy.enjoytrip.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority; // ✅ 정확한 import

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private Long id;
    private String email;

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user.getId(), user.getEmail());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 권한은 지금 사용하지 않음
    }

    @Override public String getPassword() { return null; } // 비밀번호 사용 안함
    @Override public String getUsername() { return email; } // 이메일을 username으로 사용
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}