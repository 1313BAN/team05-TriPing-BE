package com.ssafy.enjoytrip.domain.user.service;

import com.ssafy.enjoytrip.domain.user.dto.SignUpResponse;
import com.ssafy.enjoytrip.domain.user.exception.UserException;
import com.ssafy.enjoytrip.exception.ErrorCode;
import com.ssafy.enjoytrip.util.ValidUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public SignUpResponse registerUser(User user) {
		if (!ValidUtil.isValidEmail(user.getEmail()) || !ValidUtil.isValidPassword(user.getPassword())) {
			throw new UserException(ErrorCode.INVALID_FORMAT);
		}

		User existing = userMapper.selectByEmail(user.getEmail());
		if (existing != null) {
			throw new UserException(ErrorCode.DUPLICATE_EMAIL);
		}

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
