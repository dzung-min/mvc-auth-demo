package io.dzung.mvcauthdemo.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventPublisher<T> {
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public void publish(T event) {
		applicationEventPublisher.publishEvent(event);
	}
}
