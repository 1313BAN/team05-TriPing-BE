package com.ssafy.enjoytrip.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.domain.user.dto.LoginResponseDTO;
import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;

	@Override
	public boolean registerUser(User user) {
		return userMapper.insertUser(user) > 0;
	}

	@Override
	public LoginResponseDTO login(User user) {
		User loginUser = userMapper.login(user);
	    if (loginUser == null)
	        return new LoginResponseDTO(false, null);
	    else return new LoginResponseDTO(true, loginUser);
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
