package io.dzung.mvcauthdemo.entity;

import java.time.LocalDateTime;

import io.dzung.mvcauthdemo.util.exception.TokenType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represent time-sensitive security token for verifying user's email address.
 * User's account will be disabled until the token is verified.
 */
@Entity
@Table(name = "verification_token")
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
    private static final int REGISTER_VERIFY_TOKEN_DURATION = 24 * 60;      // minutes
    private static final int CHANGE_PASSWORD_VERIFY_TOKEN_DURATION = 15;    // minutes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private boolean isInvoked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiredTime;

    public VerificationToken(User user, String token, TokenType type) {
        this.token = token;
        this.user = user;
        this.isInvoked = false;
        this.type = type;
        if (type == TokenType.VERIFY_REGISTER_EMAIL) expiredTime = LocalDateTime.now().plusMinutes(REGISTER_VERIFY_TOKEN_DURATION);
        if (type == TokenType.VERIFY_CHANGE_PASSWORD_REQUEST) expiredTime = LocalDateTime.now().plusMinutes(CHANGE_PASSWORD_VERIFY_TOKEN_DURATION);
    }
}
