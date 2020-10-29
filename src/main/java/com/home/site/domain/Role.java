package com.home.site.domain;

import org.springframework.security.core.*;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}