package com.ssafy.enjoytrip.domain.attraction.exception;

import com.ssafy.enjoytrip.exception.CustomException;
import com.ssafy.enjoytrip.exception.ErrorCode;

public class AttractionException extends CustomException {
    public AttractionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
