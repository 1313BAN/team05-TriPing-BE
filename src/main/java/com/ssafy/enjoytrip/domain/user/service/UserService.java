package com.ssafy.enjoytrip.domain.user.service;

import com.ssafy.enjoytrip.domain.user.dto.UserIdResponse;
import com.ssafy.enjoytrip.domain.user.model.User;

public interface UserService {
	   UserIdResponse registerUser(User user);
	   User getUserById(Long userId);       // id로 회원 조회
	UserIdResponse updateUser(Long userId, User updatedUser);
	void deleteUser(Long userId);

}