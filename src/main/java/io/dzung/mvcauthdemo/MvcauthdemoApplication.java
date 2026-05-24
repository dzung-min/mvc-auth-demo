package io.dzung.mvcauthdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MvcauthdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcauthdemoApplication.class, args);
	}
}
