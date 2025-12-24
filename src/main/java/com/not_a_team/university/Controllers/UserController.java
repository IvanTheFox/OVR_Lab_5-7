package com.not_a_team.university.Controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Services.UserService;

/**
 * Класс для обработки запросов, связанных с пользователями
 */
@RestController
public class UserController {
    private UserService userService;

    /**
     * Конструктор, создающий объект-контроллер
     * @param userService - сервис для работы с пользователями
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обрабочик запроса на получение информации пользователя по идентификатору
     * @param id - идентификатор пользователя
     * @return - информация о пользователе
     */
    @GetMapping("/userinfobyid/{id}")
    public ResponseEntity<User> getUserInfoById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isPresent())
            return ResponseEntity.ok(user.get());
        else
            return ResponseEntity.notFound().build();
    }

    /**
     * Обрабочик запроса на получение информации пользователя по имени
     * @param name - имя пользователя
     * @return - информация о пользователе
     */
    @GetMapping("/userinfobyname/{name}")
    public ResponseEntity<User> getUserInfoByName(@PathVariable("name") String name) {
        Optional<User> user = userService.getUserByUsername(name);

        if (user.isPresent())
            return ResponseEntity.ok(user.get());
        else
            return ResponseEntity.notFound().build();
    }
}
