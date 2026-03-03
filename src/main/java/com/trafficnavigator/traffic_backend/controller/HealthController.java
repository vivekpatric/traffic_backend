package com.trafficnavigator.traffic_backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public String getHealth(){
        return "Traffic health check ";
    }
}
