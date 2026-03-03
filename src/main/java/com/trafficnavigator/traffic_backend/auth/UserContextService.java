package com.trafficnavigator.traffic_backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContextService {
    private final UserRepository userRepository;

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth==null || !auth.isAuthenticated()){
            throw new RuntimeException("User not authenticated");
        }
        String email = auth.getName();
        return userRepository.findByemail(email).orElseThrow(()-> new RuntimeException("User not found"));
    }
}
