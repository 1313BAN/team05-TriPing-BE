package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.model.dto.UserDTO;

public interface UserService {
	   boolean registerUser(UserDTO user);
	   UserDTO login(UserDTO user);
	}