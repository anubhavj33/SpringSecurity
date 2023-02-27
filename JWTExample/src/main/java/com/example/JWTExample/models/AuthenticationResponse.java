package com.example.JWTExample.models;

import java.io.Serializable;

public class AuthenticationResponse  implements Serializable {
    private String Jwt;

    public AuthenticationResponse(String jwt) {
        this.Jwt = jwt;
    }

    public String getJwt() {
        return Jwt;
    }
}
