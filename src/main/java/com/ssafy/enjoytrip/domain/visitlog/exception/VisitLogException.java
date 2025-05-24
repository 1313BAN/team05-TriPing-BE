package com.ssafy.enjoytrip.domain.visitlog.exception;

import com.ssafy.enjoytrip.exception.CustomException;
import com.ssafy.enjoytrip.exception.ErrorCode;

public class VisitLogException extends CustomException {
    public VisitLogException(ErrorCode errorCode) {
        super(errorCode);
    }
}
