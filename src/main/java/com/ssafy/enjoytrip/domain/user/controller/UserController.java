package com.ssafy.enjoytrip.domain.user.controller;

import com.ssafy.enjoytrip.domain.user.dto.SignUpResponse;
import com.ssafy.enjoytrip.domain.user.exception.UserException;
import com.ssafy.enjoytrip.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.domain.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<SignUpResponse> register(@RequestBody User user) {
		return ResponseEntity.ok(userService.registerUser(user));
	}


	@GetMapping("/mypage")
	public ResponseEntity<User> mypage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		if (user == null) throw new UserException(ErrorCode.USER_NOT_FOUND);
		return ResponseEntity.ok(user);
	}

	// 유저정보 수정
	@PostMapping("/update")
	public String updateUser(User updatedUser, HttpSession session) {

		userService.updateUser(updatedUser);

		session.setAttribute("user", updatedUser);

		// 4. 다시 마이페이지로 이동
		return "redirect:/user/mypage";
	}
}