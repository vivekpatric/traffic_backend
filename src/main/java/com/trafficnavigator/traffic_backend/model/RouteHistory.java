package com.trafficnavigator.traffic_backend.model;

import com.trafficnavigator.traffic_backend.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Table(name ="route_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double sourceLat;
    private double sourceLng;
    private double destLat;
    private double destLng;

    private double distanceKm;
    private double durationMin;
    private double trafficEtaMin;

    private String trafficLevel;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
