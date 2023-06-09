package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.User;
import com.codeup.plantapp.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUserForm";
    }

    //messing with it for the create user
    @PostMapping("/create")
    public String createUserProfile(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/userProfile";
    }
    @GetMapping("/login")
    public String showLoginForm(){
        return "/login";
    }
    @PostMapping("/login")
    public String loginSessionSetter(Model model, HttpSession session){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("user", user);
        return "/login";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/{id}/edit")
    public String editUserProfileForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "editUserForm";
    }

    @PostMapping("/{id}/edit")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        user.setUsername(updatedUser.getUsername());
        userRepository.save(user);
        return "redirect:/users/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteUserProfile(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }
}

