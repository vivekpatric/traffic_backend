package com.trafficnavigator.traffic_backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public void register(RegisterRequest req){
        User u = new User();
        u.setEmail(req.email);
        u.setPassword(encoder.encode(req.password));
        repo.save(u);
    }
    public String login(LoginRequest req){
       User u = repo.findByemail(req.email)
               .orElseThrow();
       if(!encoder.matches(req.password,u.getPassword())){
           throw new RuntimeException("Invalid Exception");
       }
       return jwt.generateToken(u.getEmail());
    }
}
