package org.micks.champmaker.auth.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserLoginFailedException extends Exception {
    public UserLoginFailedException(String message) {
        super(message);
        log.warn(message);
    }
}
