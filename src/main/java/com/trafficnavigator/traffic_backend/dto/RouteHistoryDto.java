package com.trafficnavigator.traffic_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteHistoryDto {
    public double sourceLat;
    public double sourceLng;
    public double destLat;
    public double destLng;
    public double distanceKm;
    public double durationMin;
    public double trafficEtaMin;
    public String trafficLevel;
}
