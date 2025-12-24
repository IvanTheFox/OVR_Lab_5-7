package com.not_a_team.university.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

/**
 * Класс-сервис для работы с пользователями
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Конструктор, создающий сервис
     * @param userRepository - репозитория пользователей
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод сохранения пользователя и синхронизации репозитория
     * @param user - пользователь
     */
    @Transactional
    public void saveUserAndCommit(User user) {
        userRepository.saveAndFlush(user);
    }

    /**
     * Метод сохранения пользователя
     * @param user - пользователь
     */
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Метод удаления пользователя из репозитория
     * @param user - пользователь
     */
    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }

    /**
     * Метод удаления пользователя из репозитория
     * @param id - идентификатор пользователя
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Метод получения пользователя по идентификатору
     * @param id - идентификатор пользователя
     * @return - пользователь
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Метод получения пользователя по имени
     * @param username - имя пользователя
     * @return - пользователь
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByName(username);
    }

    /**
     * Метод получения всех пользователей
     * @return - пользовтели
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Метод получения пользователя по сессии
     * @param sessionId - идентификатор сессии
     * @return - пользователь
     */
    public Optional<User> getUserBySession(String sessionId) {
        ArrayList<User> users = new ArrayList<User>(this.getAllUsers());
        for (User user : users) {
            if (user.findSession(sessionId))
                return Optional.of(user);
        }
        return Optional.empty();
    }

    /**
     * Метод получения пользователя по сессии
     * @param session - сессия
     * @return - пользователь
     */
    public Optional<User> getUserBySession(HttpSession session) {
        return this.getUserBySession(session.getId());
    }

    /**
     * Метод проверки данных входа
     * @param sessionId - идентификатор сессии
     * @param username - имя пользователя
     * @param password - пароль пользователя
     * @return - соответствие данных
     */
    public boolean checkLoginInfo(String sessionId, String username, String password) {
        Optional<User> _user = this.getUserByUsername(username);
        if (_user.isEmpty())
            return false;
        User user = _user.get();

        if (!password.equals(user.getPassword()))
            return false;

        return true;
    }

    /**
     * Метод проверки данных входа
     * @param session - сессия
     * @param username - имя пользователя
     * @param password - пароль пользователя
     * @return - соответствие данных
     */
    public boolean checkLoginInfo(HttpSession session, String username, String password) {
        return checkLoginInfo(session.getId(), username, password);
    }
}
