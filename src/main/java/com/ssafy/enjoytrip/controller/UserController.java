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
	@GetMapping("/mypage")
	public String mypage(HttpSession session) {
	    if (session.getAttribute("user") == null) {
	        // 로그인 안 한 상태면 로그인 화면으로 리다이렉트
	        return "redirect:/";  // 또는 로그인 모달 뜨게
	    }
	    return "pages/mypage";  // (mypage.jsp 위치에 따라 경로 조정)
	}
	
	//유저정보 수정
	@PostMapping("/update")
	public String updateUser(UserDTO updatedUser, HttpSession session) {

	    UserService.updateUser(updatedUser);

	    session.setAttribute("user", updatedUser);

	    // 4. 다시 마이페이지로 이동
	    return "redirect:/user/mypage";
	}
}