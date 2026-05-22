package io.dzung.mvcauthdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import io.dzung.mvcauthdemo.dto.RegisterDto;


@Controller
public class AuthController {
    @GetMapping("/register")
    public String showRegisterForm(@ModelAttribute("user") RegisterDto registerDto) {
        return "register-form";
    }
}
