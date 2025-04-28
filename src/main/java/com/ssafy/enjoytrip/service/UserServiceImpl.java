package com.ssafy.enjoytrip.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.model.dto.UserDTO;
import com.ssafy.enjoytrip.model.mapper.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public boolean registerUser(UserDTO user) {
		return userRepository.insertUser(user) > 0;
	}

	@Override
	public UserDTO login(UserDTO user) {
		return userRepository.login(user);
	}

	 @Override
	    public boolean updateUser(UserDTO user) {
	        return userRepository.updateUser(user) > 0;
	    }

	    @Override
	    public UserDTO getUserById(Long userId) {
	        return userRepository.selectUserById(userId.intValue());
	    }
}
