package com.ssafy.enjoytrip.exception;

public record ErrorResponse(ErrorCode errorCode, String message) {}