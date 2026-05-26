package io.dzung.mvcauthdemo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// @formatter:off
public record RegisterDto(
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long") 
    String password,

    @NotBlank(message = "Confirm password is required")
    String confirmPassword
) {
    public boolean isPasswordMatch() {
        if (password == null || confirmPassword == null) return false;
        return password.equals(confirmPassword);
    }

    public RegisterDto() {
        this(null, null, null);
    }
}
// @formatter:on