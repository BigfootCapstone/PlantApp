package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.User;
import com.codeup.plantapp.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository usersDao;

    public UserController(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUserForm";
    }

    //messing with it for the create user
    @PostMapping("/create")
    public String createUserProfile(@ModelAttribute("user") User user) {
        usersDao.save(user);
        return "redirect:/userProfile";
    }
    @GetMapping("/login")
    public String viewLoginPage(@PathVariable Long id) {
        User user = usersDao.findById(id)
        .formLogin()
                .loginPage("/login")
                .usernameParameter("email");
        return "/login";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/{id}/edit")
    public String editUserProfileForm(@PathVariable Long id, Model model) {
        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "editUserForm";
    }

    @PostMapping("/{id}/edit")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        user.setUsername(updatedUser.getUsername());
        usersDao.save(user);
        return "redirect:/users/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteUserProfile(@PathVariable Long id) {
        usersDao.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersDao.findAll());
        return "users";
    }
}

