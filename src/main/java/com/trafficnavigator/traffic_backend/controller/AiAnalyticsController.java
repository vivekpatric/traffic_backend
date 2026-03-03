package com.trafficnavigator.traffic_backend.controller;

import com.trafficnavigator.traffic_backend.service.TrafficAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiAnalyticsController {
    public final TrafficAnalyticsService analyticsService;

    @GetMapping("/hotspots")
    public Map<String,Integer> getHotspots(){
        return analyticsService.getHotspots();
    }
    @GetMapping("/traffic-count")
    public Map<String,Integer> getTrafficCount(){
        return analyticsService.getTrafficCounts();
    }

    @GetMapping("/predict")
    public Map<String,String> predict (@RequestParam double destLat,
        @RequestParam double destLng){
       String prediction =analyticsService.predictNextTenMinutes(destLat,destLng);
       return Map.of("prediction",prediction);
    }
}
