package com.not_a_team.university.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**
 * Класс, предоставляющий прочую информацию пользователю
 */
public class MiscController {
    /**
     * Обработчик запроса на получение серверного времени
     * @return - серверное время
     */
    @GetMapping("/getservertime")
    public String getServerTime() {
        return String.valueOf(System.currentTimeMillis());
    }
}
