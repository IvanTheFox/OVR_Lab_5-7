package com.not_a_team.university.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscController {
    @GetMapping("/getservertime")
    public Long getServerTime() {
        return System.currentTimeMillis() / 1000L;
    }
}
