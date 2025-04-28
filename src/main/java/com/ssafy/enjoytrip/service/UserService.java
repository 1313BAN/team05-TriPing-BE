package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.model.dto.UserDTO;

public interface UserService {
	   boolean registerUser(UserDTO user);
	   UserDTO login(UserDTO user);
	   
	   boolean updateUser(UserDTO user);     // 회원정보 수정
	   UserDTO getUserById(Long userId);       // id로 회원 조회
	}