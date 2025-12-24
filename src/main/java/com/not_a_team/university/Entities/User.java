package com.not_a_team.university.Entities;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.not_a_team.university.Enums.Role;
import com.not_a_team.university.Services.FileService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpSession;

/**
 * Класс, представляющий зарегистрированного пользователя
 */
@Entity
@Table(name = "users")
public class User {
    private static String avatarPath = "static\\uploads\\avatars\\";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    private Role role;
    private ArrayList<String> sessions;
    private String avatar;
    private int loginCount;

    /**
     * Базовый конструктор
     */
    public User() {}

    /**
     * Конструктор с именем, паролем и ролью
     * @param name - имя пользователя
     * @param password - пароль пользователя
     * @param role - роль пользователя
     */
    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.sessions = new ArrayList<String>();
    }

    /**
     * Конструктор с именем, паролем, ролью и идентификатором активной сессии
     * @param name - имя пользователя
     * @param password - пароль пользователя
     * @param role - роль пользователя
     * @param sessionId - идентификатор активной сессии
     */
    public User(String name, String password, Role role, String sessionId) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.sessions = new ArrayList<String>();
        
        this.addSesseion(sessionId);
    }

    /**
     * Конструктор с именем, паролем, ролью и активной сессией
     * @param name - имя пользователя
     * @param password - пароль пользователя
     * @param role - роль пользователя
     * @param session - активная сессия
     */
    public User(String name, String password, Role role, HttpSession session) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.sessions = new ArrayList<String>();
        
        this.addSesseion(session.getId());
    }
    
    public Long getId() {
        return this.id;
    }
    public void setId(Long newId) {
        this.id = newId;
    }
    public String getUsername() {
        return this.name;
    }
    public void setUsername(String newusername) {
        this.name = newusername;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
    public Role getRole() {
        return this.role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public int getLoginCount() {
        return this.loginCount;
    }
    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Метод установления аватара
     * @param file - файл изображения
     * @throws IOException - ошибка при сохранения файла на сервер
     */
    public void setAvatar(MultipartFile file) throws IOException {
        if (this.avatar != null)
            FileService.deleteFile(avatarPath + this.avatar);
        String fileName = Long.toString(this.id);
        this.avatar = FileService.saveFile(file, avatarPath, fileName);
    }

    /**
     * Метод добавления новой активной сессии
     * @param sessionId - идентификатор сессии
     */
    public void addSesseion(String sessionId) {
        if (!this.sessions.contains(sessionId)) {
            this.sessions.add(sessionId);
            loginCount++;
        }
    }

    /**
     * Метод добавления новой активной сессии
     * @param session - сессия
     */
    public void addSesseion(HttpSession session) {
        addSesseion(session.getId());
    }
    
    /**
     * Метод проверки наличия сессии
     * @param sessionId - идентификатор сессии
     * @return - наличие сессии
     */
    public boolean findSession(String sessionId) {
        return this.sessions.contains(sessionId);
    }

    /**
     * Метод проверки наличия сессии
     * @param session - сессия
     * @return - наличие сессии
     */
    public boolean findSession(HttpSession session) {
        return this.findSession(session.getId());
    }
    
    /**
     * Метод удаления активной сессии
     * @param sessionId - идентификатор сессии
     * @throws NoSessionFound - ошибка при отсутствии такой сессии
     */
    public void removeSession(String sessionId) throws NoSessionFound {
        if (this.sessions.contains(sessionId))
            this.sessions.remove(sessionId);
        else
            throw new NoSessionFound(sessionId);
    }

    /**
     * Метод удаления активной сессии
     * @param session - сессия
     * @throws NoSessionFound - ошибка при отсутствии такой сессии
     */
    public void removeSession(HttpSession session) throws NoSessionFound {
        removeSession(session.getId());
    }

    /**
     * Класс исключения отсутствия искомой сессии
     */
    public class NoSessionFound extends Exception {
        public NoSessionFound(String sessionId) {
            super("Session with id <" + sessionId + "> could not be found");
        }
    }
}
