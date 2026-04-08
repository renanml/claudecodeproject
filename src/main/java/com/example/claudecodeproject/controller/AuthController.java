package com.example.claudecodeproject.controller;

import com.example.claudecodeproject.dto.AuthRequest;
import com.example.claudecodeproject.dto.AuthResponse;
import com.example.claudecodeproject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final long expiration;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          @Value("${jwt.expiration}") long expiration) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.expiration = expiration;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token, expiration));
    }
}
