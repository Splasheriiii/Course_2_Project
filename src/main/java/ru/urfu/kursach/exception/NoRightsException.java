package ru.urfu.kursach.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="No rights to use User-Specific entity")  // 404
@Getter
public class NoRightsException extends RuntimeException {
    public NoRightsException(Long objectId, String objectType) {
        super(String.format("No rights to use User-Specific entity %s(%d)", objectType, objectId));
    }
}
