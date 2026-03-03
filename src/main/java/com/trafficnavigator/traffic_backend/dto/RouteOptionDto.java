package com.trafficnavigator.traffic_backend.dto;

import org.springframework.stereotype.Component;

@Component
public class RouteOptionDto {
    public String routeId;
    public double distanceKm;
    public double baseEtaMin;
    public double trafficEtaMin;
    public String trafficLevel;
    public Object geometry;
}
