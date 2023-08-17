package com.test.realworldexample.security;

public interface AuthenticationServiceI {
    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse login(LoginRequest request);

    public void logout(String token);
}
