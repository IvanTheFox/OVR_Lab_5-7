package com.not_a_team.university.Controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.OptionalInt;

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
    public String editProfile(HttpSession session, MultipartFile avatar, @RequestParam Long id, @RequestParam Optional<String> name, @RequestParam Optional<String> password, @RequestParam OptionalInt permLevel, @RequestParam OptionalInt loginCount) {
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

        if (name.isPresent())
            user.setUsername(name.get());
        if (password.isPresent())
            user.setPassword(password.get());
        if (permLevel.isPresent())
            user.setRole(Role.getRoleFromPermLevel(permLevel.getAsInt()));
        if (loginCount.isPresent())
            user.setLoginCount(loginCount.getAsInt());

        userService.saveUser(user);

        return "Successfuly update user!";
    }
}
