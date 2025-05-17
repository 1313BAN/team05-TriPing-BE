package com.ssafy.enjoytrip.domain.user.controller;

import com.ssafy.enjoytrip.auth.jwt.UserPrincipal;
import com.ssafy.enjoytrip.domain.user.dto.PasswordChangeRequest;
import com.ssafy.enjoytrip.domain.user.dto.UserIdResponse;
import com.ssafy.enjoytrip.domain.user.exception.UserException;
import com.ssafy.enjoytrip.exception.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// logout
	@GetMapping("/me")
	public ResponseEntity<User> getMyInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		if (userPrincipal == null) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}
		User user = userService.getUserById(userPrincipal.getId());
		return ResponseEntity.ok(user);
	}

	// 유저정보 수정
	@PutMapping("/me")
	public ResponseEntity<UserIdResponse> updateMyInfo(
			@AuthenticationPrincipal UserPrincipal userPrincipal,
			@RequestBody User updatedData) {

		if (userPrincipal == null) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}

		UserIdResponse response = userService.updateUser(userPrincipal.getId(), updatedData);
		return ResponseEntity.ok(response); // 200 OK + JSON body
	}

	//유저정보 삭제
	@DeleteMapping("/me")
	public ResponseEntity<Void> deleteMyAccount(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		if (userPrincipal == null) {
			throw new UserException(ErrorCode.USER_NOT_FOUND);
		}

		userService.deleteUser(userPrincipal.getId());
		return ResponseEntity.noContent().build(); // 204 응답
	}

	//비밀번호 변경
	@PutMapping("/password")
	public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserPrincipal userPrincipal,
											   @RequestBody PasswordChangeRequest request){
		System.out.println(request);
		userService.changePassword(userPrincipal.getId(),request);
		return ResponseEntity.noContent().build();
	}

}