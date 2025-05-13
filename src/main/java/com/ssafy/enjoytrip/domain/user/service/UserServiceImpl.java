package com.ssafy.enjoytrip.domain.user.service;

import com.ssafy.enjoytrip.domain.user.dto.SignUpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public SignUpResponse registerUser(User user) {
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userMapper.insertUser(user);

		// 방금 등록된 user 반환 (id 포함해서)
		return new SignUpResponse(user.getId());
	}



	 @Override
	    public boolean updateUser(User user) {
	        return userMapper.updateUser(user) > 0;
	    }

	    @Override
	    public User getUserById(Long userId) {
	        return userMapper.selectUserById(userId.intValue());
	    }
}
