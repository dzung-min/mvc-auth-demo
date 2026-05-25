package io.dzung.mvcauthdemo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represent time-sensitive security token for verifying user's email address.
 * User's account will be disable until the token is verified.
 */
@Entity
@Table(name = "verification_token")
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
    private static final int DURATION = 24; // hours

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "UUID", nullable = false, unique = true)
    private String token;

    private boolean isInvoked;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiredTime;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.isInvoked = false;
        this.expiredTime = LocalDateTime.now().plusHours(DURATION);
    }
}
