package com.ssafy.enjoytrip.auth.controller;

import com.ssafy.enjoytrip.auth.dto.LoginRequestDTO;
import com.ssafy.enjoytrip.auth.service.AuthService;
import com.ssafy.enjoytrip.domain.user.dto.UserIdResponse;
import com.ssafy.enjoytrip.domain.user.model.User;
import com.ssafy.enjoytrip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserIdResponse> register(@RequestBody User user) {
        UserIdResponse response = userService.registerUser(user);
        URI location = URI.create("/user/" + response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String token = authService.login(request);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .build();  // 바디 없어도 됨
    }
}