package com.steam.management.exception;

import com.steam.management.exception.custom.NotFoundUserException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = {RestController.class, Service.class})
@Order(0)
public class ManagementExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundUserException.class)
    protected ResponseEntity<String> handleNotFoundUserException(NotFoundUserException e) {
        System.out.println(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("이미 삭제되었거나, 존재하지 않는 유저 입니다.");
    }
}
