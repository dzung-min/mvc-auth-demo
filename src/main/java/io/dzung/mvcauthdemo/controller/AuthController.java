package io.dzung.mvcauthdemo.controller;

import io.dzung.mvcauthdemo.entity.VerificationToken;
import io.dzung.mvcauthdemo.util.exception.TokenType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.dzung.mvcauthdemo.event.EventPublisher;
import io.dzung.mvcauthdemo.dto.RegisterDto;
import io.dzung.mvcauthdemo.event.AuthEvent;
import io.dzung.mvcauthdemo.entity.User;
import io.dzung.mvcauthdemo.service.UserService;
import io.dzung.mvcauthdemo.service.VerificationTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {
	private final UserService userService;
	private final EventPublisher<AuthEvent> eventPublisher;
	private final VerificationTokenService verificationTokenService;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new RegisterDto());
		return "register-form";
	}

	@PostMapping("/register")
	public String processRegistration(@ModelAttribute("user") @Valid RegisterDto registerDto,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "register-form";
		}
		if (!registerDto.isPasswordMatch()) {
			bindingResult.rejectValue("confirmPassword", "password_mismatch", "Confirm password mismatch");
			return "register-form";
		}
		User existingUser = userService.getUserByEmail(registerDto.email());
		if (existingUser != null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Email Already Exists");
			return "redirect:/register";
		}

		User user = userService.createUser(registerDto);
		VerificationToken token = verificationTokenService.createToken(user, TokenType.VERIFY_REGISTER_EMAIL);
		AuthEvent event = new AuthEvent(user, token);
		eventPublisher.publish(event);

		redirectAttributes.addFlashAttribute("successMessage", "Your account has been created. A verification email will be send shortly.");
		return "redirect:/login";
	}

	@GetMapping("/register-verification")
	public String processVerificationToken(@RequestParam("token") String passingToken, RedirectAttributes redirectAttributes, Model model) {
		VerificationToken verificationToken = verificationTokenService.getToken(passingToken);
		if (verificationToken == null || verificationToken.getType() != TokenType.VERIFY_REGISTER_EMAIL) {
			redirectAttributes.addFlashAttribute("errorMessage", "Token not found. Please register.");
			return "redirect:/register";
		}
		VerificationToken currentInvokedToken = verificationTokenService.markTokenAsInvolved(verificationToken);
		if (verificationTokenService.isTokenExpired(currentInvokedToken)) {
			model.addAttribute("user_id", currentInvokedToken.getUser().getId());
			return "generate-token";
		}
		userService.enableUser(verificationToken.getUser());
		redirectAttributes.addFlashAttribute("successMessage", "Your account has been verified. Please login.");
		return "redirect:/login";
	}

	@PostMapping("/reissue")
	public String reissueEmail(Long user_id, RedirectAttributes redirectAttributes) {
		User user = userService.getUserById(user_id);
		if (user == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "You don't have an account. Please register.");
			return "redirect:/register";
		}
		VerificationToken token = verificationTokenService.createToken(user, TokenType.VERIFY_REGISTER_EMAIL);
		AuthEvent event = new AuthEvent(user, token);
		eventPublisher.publish(event);
		redirectAttributes.addFlashAttribute("successMessage", "Success. You will receive a verification email shortly.");
		return "redirect:/login";
	}
}
