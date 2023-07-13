package org.micks.champmaker.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PasswordService {

    public String encryptPassword(String password) {
        log.info("Will encrypt password");
        return "mockedPassword";
    }
}
