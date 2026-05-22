package io.dzung.mvcauthdemo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.dzung.mvcauthdemo.dto.RegisterDto;
import io.dzung.mvcauthdemo.model.User;
import io.dzung.mvcauthdemo.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    @GetMapping("/register")
    public String showRegisterForm(@ModelAttribute("user") RegisterDto registerDto) {
        return "register-form";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("user") @Valid RegisterDto registerDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register-form";
        }
        if (!registerDto.isPasswordMatch()) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Confirm password doesn't match");
            return "register-form";
        }
        User user = new User();
        user.setEmail(registerDto.email());
        user.setPassword(passwordEncoder.encode(registerDto.password()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("email_duplicated", "Email is already in use");
            return "redirect:/register";
        }
        redirectAttributes.addFlashAttribute("register_success", "Your account has been created");
        return "redirect:/login";
    }
}
