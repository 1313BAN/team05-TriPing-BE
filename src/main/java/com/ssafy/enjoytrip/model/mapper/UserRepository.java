package com.ssafy.enjoytrip.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.dto.UserDTO;


@Mapper
public interface UserRepository {
   int insertUser(UserDTO User);
   UserDTO login(UserDTO User);
   
   // ⭐ 추가: 회원정보 수정 메서드
   int updateUser(UserDTO user);

   // ⭐ 추가: id로 회원정보 조회하는 메서드
   UserDTO selectUserById(int userId);
}