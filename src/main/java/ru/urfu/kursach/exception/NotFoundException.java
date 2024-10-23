package ru.urfu.kursach.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User-Specific entity not found")
public class NotFoundException extends RuntimeException {
    public NotFoundException(String objectType) {
        super(String.format("User-Specific entity(%s) not found", objectType));
    }
}
