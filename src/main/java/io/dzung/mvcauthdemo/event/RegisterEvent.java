package io.dzung.mvcauthdemo.event;

import io.dzung.mvcauthdemo.entity.User;

public record RegisterEvent(User user, String token) {
}
