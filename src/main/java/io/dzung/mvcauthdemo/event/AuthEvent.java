package io.dzung.mvcauthdemo.event;

import io.dzung.mvcauthdemo.entity.User;
import io.dzung.mvcauthdemo.entity.VerificationToken;

public record AuthEvent(User user, VerificationToken token) {
}
