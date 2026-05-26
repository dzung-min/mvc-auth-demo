package io.dzung.mvcauthdemo.security;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.dzung.mvcauthdemo.entity.User;

public record AuthUser(User user) implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return user.getEmail();
    }
}
