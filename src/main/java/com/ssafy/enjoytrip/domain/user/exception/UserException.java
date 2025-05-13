package com.ssafy.enjoytrip.domain.user.exception;

import com.ssafy.enjoytrip.exception.CustomException;
import com.ssafy.enjoytrip.exception.ErrorCode;

public class UserException extends CustomException {
    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}