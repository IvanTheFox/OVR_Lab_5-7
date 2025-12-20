package com.not_a_team.university.Controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.not_a_team.university.Entities.User;
import com.not_a_team.university.Enums.Role;
import com.not_a_team.university.Services.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
public class ProfileEditorController {
    private UserService userService;

    public ProfileEditorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/editprofile")
    public String editProfile(HttpSession session, MultipartFile avatar, @RequestParam Long id, @RequestParam String name, @RequestParam String password, @RequestParam int permLevel, @RequestParam int loginCount) {
        Optional<User> _thisuser = userService.getUserBySession(session);
        if (_thisuser.isEmpty())
            return "You are not a user!";
        User thisuser = _thisuser.get();

        if (thisuser.getRole() != Role.Admin)
            return "Insufficient permissions for editing profiles!";
        
        Optional<User> _user = userService.getUserById(id);
        if (_user.isEmpty())
            return "User with provided id does not exist!";
        User user = _user.get();

        if (avatar != null) {
            try {
                user.setAvatar(avatar);
            } catch (IOException exception) {}
        }

        user.setUsername(name);
        user.setPassword(password);
        user.setRole(Role.getRoleFromPermLevel(permLevel));
        user.setLoginCount(loginCount);

        userService.saveUser(user);

        return "Successfuly update user!";
    }
}
