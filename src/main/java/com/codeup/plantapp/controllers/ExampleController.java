package com.codeup.plantapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @GetMapping("/error")
    public void throwError() {
        throw new RuntimeException("Testing custom error page");
    }
}
