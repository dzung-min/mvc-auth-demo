package io.dzung.mvcauthdemo.service;

import java.util.Optional;

import io.dzung.mvcauthdemo.security.AuthUser;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.dzung.mvcauthdemo.entity.User;
import io.dzung.mvcauthdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public @NullMarked UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(AuthUser::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
