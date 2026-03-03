package com.trafficnavigator.traffic_backend.kafka.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteSelectedEvent {
    private String email;
    private double sourceLat;
    private double sourceLng;
    private double destLat;
    private double destLng;
    private String trafficLevel;
    private double distanceKm;
    private double durationMin;
    private double trafficEtaMin;
    private long timestamp;
}
