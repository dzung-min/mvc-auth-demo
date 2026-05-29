package io.dzung.mvcauthdemo.service;

import java.time.LocalDateTime;
import java.util.UUID;

import io.dzung.mvcauthdemo.util.exception.TokenType;
import org.springframework.stereotype.Service;

import io.dzung.mvcauthdemo.entity.User;
import io.dzung.mvcauthdemo.entity.VerificationToken;
import io.dzung.mvcauthdemo.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
	private final VerificationTokenRepository verificationTokenRepository;
	
	@Transactional
	public VerificationToken createToken(User user, TokenType tokenType) {
		String token;
		VerificationToken existingToken;
		do {
			token = UUID.randomUUID().toString();
			existingToken = verificationTokenRepository.findByToken(token).orElse(null);
		} while (existingToken != null);
		VerificationToken verificationToken = new VerificationToken(user, token, tokenType);
		return verificationTokenRepository.save(verificationToken);
	}

	public VerificationToken getToken(String token) {
		return verificationTokenRepository.findByToken(token).orElse(null);
	}

	public VerificationToken markTokenAsInvolved(VerificationToken token) {
		token.setInvoked(true);
		return verificationTokenRepository.save(token);
	}
	@Transactional
	public boolean isTokenExpired(VerificationToken token) {
		return token.getExpiredTime().isBefore(LocalDateTime.now());
	}
}
