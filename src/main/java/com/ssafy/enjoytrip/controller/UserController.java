package com.ssafy.enjoytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.enjoytrip.model.dto.UserDTO;
import com.ssafy.enjoytrip.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService UserService;

	@PostMapping("/signup")
	public String register(@ModelAttribute UserDTO User) {
		boolean result = UserService.registerUser(User);
		if (result) {
			return "index";
		} else {
			return "index";
		}
	}

	@PostMapping("/login")
	public String login(@ModelAttribute UserDTO User, HttpSession session) {
		UserDTO loginUser = UserService.login(User);
		if (loginUser != null) {
			session.setAttribute("user", loginUser);
			return "index";
		} else {
			return "index";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	// GET 매핑으로 user/mypage 이런 식으로 들어온다면 -> session에서 user를 꺼내서 그 정보를 마이페이지에 출력해서 forwarding
}