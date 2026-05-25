package io.dzung.mvcauthdemo.event;

import io.dzung.mvcauthdemo.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegisterEvent {
	private final User user;
	private final String token;
}
