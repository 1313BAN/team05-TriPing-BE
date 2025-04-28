package com.ssafy.enjoytrip.model.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long userId;
    private String userEmail;
    private String userPassword;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}