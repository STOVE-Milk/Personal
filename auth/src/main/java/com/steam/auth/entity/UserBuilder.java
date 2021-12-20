package com.steam.auth.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class UserBuilder{

    private Integer b_id;
    private String b_email;
    private String b_password;
    private String b_nickname;
    private Boolean b_role;
    private Boolean b_verified;
    private String b_code;

    public UserBuilder id(Integer id) {
        this.b_id = id;
        return this;
    }

    public UserBuilder email(String email) {
        this.b_email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.b_password = password;
        return this;
    }

    public UserBuilder nickname(String nickname) {
        this.b_nickname = nickname;
        return this;
    }

    public UserBuilder role(boolean role) {
        this.b_role = role;
        return this;
    }

    public UserBuilder verified(Boolean verified) {
        this.b_verified = verified;
        return this;
    }

    public UserBuilder code(String code) {
        this.b_code = code;
        return this;
    }

    public User build(){
        if(this.b_role == null) this.b_role = false;
        if(this.b_verified == null) this.b_verified = false;
        return new User(b_id, b_email, b_password, b_nickname, b_role, b_verified, b_code);
    }
}