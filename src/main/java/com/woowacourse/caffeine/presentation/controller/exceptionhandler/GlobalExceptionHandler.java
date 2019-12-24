package com.woowacourse.caffeine.presentation.controller.exceptionhandler;

import com.woowacourse.caffeine.application.exception.ErrorResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.woowacourse.caffeine.presentation.controller"})
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ErrorResponse> handleErrorResponse(ErrorResponseException e) {
        return ResponseEntity.status(e.getStatus())
            .body(new ErrorResponse("Fail", e.getMessage()));
    }
}
