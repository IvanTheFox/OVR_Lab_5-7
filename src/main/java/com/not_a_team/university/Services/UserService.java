package com.not_a_team.university.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Manage users
    @Transactional
    public void saveUserAndCommit(User user) {
        userRepository.saveAndFlush(user);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    // Get users
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByName(username);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserBySession(String sessionId) {
        ArrayList<User> users = new ArrayList<User>(this.getAllUsers());
        for (User user : users) {
            if (user.findSession(sessionId))
                return Optional.of(user);
        }
        return Optional.empty();
    }
    public Optional<User> getUserBySession(HttpSession session) {
        return this.getUserBySession(session.getId());
    }
    // Attempt login
    public boolean checkLoginInfo(String sessionId, String username, String password) {
        // Checking if user exists
        Optional<User> _user = this.getUserByUsername(username);
        if (_user.isEmpty())
            return false;
        User user = _user.get();

        // Checking if the passwords match
        if (!password.equals(user.getPassword()))
            return false;

        // Successful login
        return true;
    }
    public boolean checkLoginInfo(HttpSession session, String username, String password) {
        return checkLoginInfo(session.getId(), username, password);
    }
}
