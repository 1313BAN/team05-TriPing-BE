package com.ssafy.enjoytrip.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.dto.UserDTO;


@Mapper
public interface UserRepository {
   int insertUser(UserDTO User);
   UserDTO login(UserDTO User);
}