package com.trafficnavigator.traffic_backend.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest req){
        service.register(req);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest req, HttpServletResponse response){
        String token = service.login(req);
        Cookie cookie = new Cookie("jwt",token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        cookie.setAttribute("SameSite","None");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();

    }
    @PostMapping("/logout")
    public ResponseEntity<?>Logout(HttpServletResponse response){
        Cookie cookie = new Cookie("jwt",null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/me")
    public ResponseEntity<?>me(){
        return ResponseEntity.ok().build();
    }

}
