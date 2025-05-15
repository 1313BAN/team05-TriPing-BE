package com.ssafy.enjoytrip.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.user.model.User;

import java.util.Optional;


@Mapper
public interface UserMapper {
   int insertUser(User User);
   void updateUser(User user);
   User selectUserById(Long userId);
   User selectByEmail(String userEmail);
   void deleteUserById(Long userId);


}