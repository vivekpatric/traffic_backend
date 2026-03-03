package com.trafficnavigator.traffic_backend.controller;

import com.trafficnavigator.traffic_backend.dto.RouteOptionsRequestDto;
import com.trafficnavigator.traffic_backend.dto.RouteOptionsResponseDto;
import com.trafficnavigator.traffic_backend.dto.RouteResponseDto;
import com.trafficnavigator.traffic_backend.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")

public class RouteController {

    private final RouteService routeService;
    public RouteController(RouteService routeService){
        this.routeService=routeService;

    }
    @PostMapping("/options")
    public RouteOptionsResponseDto calculate (@RequestBody RouteOptionsRequestDto req) throws Exception{
        return routeService.getRouteOptions(
                req.sourceLat,req.sourceLng,req.destLat,req.destLng
        );
    }
}
