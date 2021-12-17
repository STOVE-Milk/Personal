package com.steam.management.entity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "role")
    private Boolean role;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "code")
    private String code;

    public void verify() {
        this.verified = true;
    }

    public void setAdmin() {
        this.role = true;
    }

    public void setNormal() {
        this.role = false;
    }
}