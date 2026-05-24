package io.dzung.mvcauthdemo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AfterRegisterEventListener {
	@EventListener
	public void listen(AfterRegisterEvent event) {
		System.out.println("User: " + event.getUser().getEmail());
		System.out.println("Token: " + event.getToken());
	}
}
