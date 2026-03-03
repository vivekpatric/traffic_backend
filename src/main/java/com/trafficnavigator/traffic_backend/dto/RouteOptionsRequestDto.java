package com.trafficnavigator.traffic_backend.dto;

import org.springframework.stereotype.Component;

@Component
public class RouteOptionsRequestDto {
    public double sourceLat;
    public double sourceLng;
    public double destLat;
    public double destLng;

}
