package io.dzung.mvcauthdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.dzung.mvcauthdemo.dto.RegisterDto;
import io.dzung.mvcauthdemo.exception.EmailExistException;
import io.dzung.mvcauthdemo.exception.PasswordMisMatchException;
import io.dzung.mvcauthdemo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(@ModelAttribute("user") RegisterDto registerDto) {
        return "register-form";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") @Valid RegisterDto registerDto,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register-form";
        }
        try {
            userService.createUser(registerDto);
        } catch (Throwable e) {
            if (e instanceof PasswordMisMatchException) {
                bindingResult.rejectValue("confirmPassword", "password_mismatch", e.getMessage());
                return "register-form";
            }
            if (e instanceof EmailExistException) {
                redirectAttributes.addFlashAttribute("email_duplicated", e.getMessage());
                return "redirect:/register";
            }
        }
        redirectAttributes.addFlashAttribute("register_success", "Your account has been created");
        return "redirect:/login";
    }
}
