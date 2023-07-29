package com.codeup.plantapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String viewLoginPage() {
//        model.addAttribute("user", new User());
//        return "redirect:/users/login";
        return "login";
    }
}