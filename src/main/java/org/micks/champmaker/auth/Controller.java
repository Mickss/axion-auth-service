package org.micks.champmaker.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Controller {

    @GetMapping
    public String string() {
        return "Hello World";
    }
}
