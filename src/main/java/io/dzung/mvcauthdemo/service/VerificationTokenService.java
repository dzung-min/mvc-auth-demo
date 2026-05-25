package io.dzung.mvcauthdemo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.dzung.mvcauthdemo.model.User;
import io.dzung.mvcauthdemo.model.VerificationToken;
import io.dzung.mvcauthdemo.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
	private final VerificationTokenRepository verificationTokenRepository;
	
	@Transactional
	public String createToken(User user) {
		String token;
		VerificationToken existingToken;
		do {
			token = UUID.randomUUID().toString();
			existingToken = verificationTokenRepository.findByToken(token).orElse(null);
		} while (existingToken != null);
		VerificationToken verificationToken = new VerificationToken(token, user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}
}
