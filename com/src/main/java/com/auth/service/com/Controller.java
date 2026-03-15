package com.auth.service.com;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class Controller {

    private final AuthService authService;

    public Controller(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(
                authService.register(request.getEmail(), request.getPassword())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(
                authService.login(request.getEmail(), request.getPassword())
        );
    }
}