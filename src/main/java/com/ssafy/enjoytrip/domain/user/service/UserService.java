package com.ssafy.enjoytrip.domain.user.service;

import com.ssafy.enjoytrip.domain.user.model.User;

public interface UserService {
	   boolean registerUser(User user);
	   boolean updateUser(User user);     // 회원정보 수정
	   User getUserById(Long userId);       // id로 회원 조회
	}