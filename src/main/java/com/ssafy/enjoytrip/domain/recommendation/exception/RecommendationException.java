package com.ssafy.enjoytrip.domain.recommendation.exception;

import com.ssafy.enjoytrip.exception.CustomException;
import com.ssafy.enjoytrip.exception.ErrorCode;

public class RecommendationException extends CustomException {
    public RecommendationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
