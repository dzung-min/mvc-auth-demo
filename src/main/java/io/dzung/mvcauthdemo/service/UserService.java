package io.dzung.mvcauthdemo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.dzung.mvcauthdemo.dto.RegisterDto;
import io.dzung.mvcauthdemo.exception.EmailExistException;
import io.dzung.mvcauthdemo.exception.PasswordMisMatchException;
import io.dzung.mvcauthdemo.model.User;
import io.dzung.mvcauthdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(RegisterDto registerDto) throws EmailExistException, PasswordMisMatchException {
        if (!registerDto.isPasswordMatch()) {
            throw PasswordMisMatchException.getInstance();
        }
        User existingUser = userRepository.findByEmail(registerDto.email()).orElse(null);
        if (existingUser != null) {
            throw EmailExistException.getInstance();
        }
        User newUser = new User();
        newUser.setEmail(registerDto.email());
        newUser.setPassword(passwordEncoder.encode(registerDto.password()));
        userRepository.save(newUser);
    }
}
