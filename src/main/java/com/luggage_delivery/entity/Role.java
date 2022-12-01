package com.luggage_delivery.entity;


import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public enum Role implements Serializable, GrantedAuthority {
    USER, MANAGER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
