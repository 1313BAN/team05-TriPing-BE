package com.ssafy.enjoytrip.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.user.model.User;


@Mapper
public interface UserMapper {
   int insertUser(User User);
   int updateUser(User user);
   User selectUserById(int userId);
   User selectByEmail(String userEmail);
}