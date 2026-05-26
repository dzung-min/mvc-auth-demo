package io.dzung.mvcauthdemo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.dzung.mvcauthdemo.dto.RegisterDto;
import io.dzung.mvcauthdemo.util.exception.EmailExistException;
import io.dzung.mvcauthdemo.util.exception.PasswordMisMatchException;
import io.dzung.mvcauthdemo.entity.User;
import io.dzung.mvcauthdemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(RegisterDto registerDto) {
        User newUser = new User();
        newUser.setEmail(registerDto.email());
        newUser.setPassword(passwordEncoder.encode(registerDto.password()));
        return userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public void enableUser(User user) {
        user.setEnable(true);
        userRepository.save(user);
    }
}
