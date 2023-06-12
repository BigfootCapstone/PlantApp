package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.User;
import com.codeup.plantapp.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository usersDao;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userDao;

    public UserController(UserRepository userDao, UserRepository usersDao, PasswordEncoder passwordEncoder){
        this.usersDao = usersDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUserForm";
    }

    //messing with it for the create user
    @PostMapping("/create")
    public String createUserProfile(@ModelAttribute("user") User user) {
        user.setCreated_at(LocalDate.now());
        userDao.save(user);
        return "redirect:/users/" + user.getId();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDao.save(user);
        return "redirect:/userProfile";
    }
    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String loginSessionSetter(Model model, HttpSession session){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("user", user);
        return "userProfile";
    }
    @GetMapping("/profile")
    public String showProfile(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        user = userDao.findUserById(userId);
        model.addAttribute("user", user);
        System.out.println(user.getUsername());
        return "userProfile";
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
        User user = userDao.findUserById(1L);
        User users = usersDao.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        users.setUsername(updatedUser.getUsername());
        user.setFirst_name(updatedUser.getFirst_name());
        user.setLast_name(updatedUser.getLast_name());
        user.setCity(updatedUser.getCity());
        user.setEmail(updatedUser.getEmail());
        userDao.save(user);
        usersDao.save(users);
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

