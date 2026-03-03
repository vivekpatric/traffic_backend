package com.trafficnavigator.traffic_backend.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteOptionsResponseDto {
    public List<RouteOptionDto> routes;
}
