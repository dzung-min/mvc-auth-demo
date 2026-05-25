package io.dzung.mvcauthdemo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isEnable;

    public User() {
        isEnable = false;
    }
}
