package com.ssafy.enjoytrip.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.exception.UnauthorizedException;
import com.ssafy.enjoytrip.domain.user.dto.LoginResponseDTO;
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
	public String register(@ModelAttribute User User) {
		boolean result = userService.registerUser(User);
		if (result) {
			return "index";
		} else {
			return "index";
		}
	}

	// 로그인

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody User user, HttpSession session) {
		LoginResponseDTO res = userService.login(user); // 서비스에서 설정 다 된거임
		if (res.isSuccess()) {
			session.setAttribute("user", res.getUser());
		}
		return res;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}

	// GET 매핑으로 user/mypage 이런 식으로 들어온다면 -> session에서 user를 꺼내서 그 정보를 마이페이지에 출력해서
	// forwarding
	/*
	 * @GetMapping("/mypage") public String mypage(HttpSession session) { if
	 * (session.getAttribute("user") == null) { // 로그인 안 한 상태면 로그인 화면으로 리다이렉트 return
	 * "redirect:/"; // 또는 로그인 모달 뜨게 } return "pages/mypage"; // (mypage.jsp 위치에 따라
	 * 경로 조정) }
	 */

	@GetMapping("/mypage")
	public ResponseEntity<User> mypage(HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		if (user == null) throw new UnauthorizedException("로그인이 필요합니다.");
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