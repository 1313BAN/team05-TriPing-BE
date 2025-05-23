package com.ssafy.enjoytrip.exception;
import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),
    INVALID_FORMAT(BAD_REQUEST, "입력된 형식이 유효하지 않습니다."),

    // auth
    INVALID_CREDENTIALS(UNAUTHORIZED, "이메일 또는 비밀번호가 틀렸습니다."),
    TOKEN_EXPIRED(UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE(UNAUTHORIZED, "잘못된 JWT 시그니처입니다."),

    // user
    USER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),
    DUPLICATE_EMAIL(CONFLICT, "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD(BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),


    // attraction
    ATTRACTION_NOT_FOUND(NOT_FOUND, "관광지를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;


    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}