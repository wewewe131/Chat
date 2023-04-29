package com.example.firstdemo.Common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonExceptionProcess {

    @ExceptionHandler({Exception.class})
    public ApiResponse<String> exception(Exception e) {
        log.error("Exception: ", e);
        return ApiResponse.fail(e.getMessage(), ResEnums.FAIL);
    }
}
