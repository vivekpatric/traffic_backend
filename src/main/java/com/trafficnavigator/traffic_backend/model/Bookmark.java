package com.trafficnavigator.traffic_backend.model;

import com.trafficnavigator.traffic_backend.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table (name="bookmarks")
@Getter @Setter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double sourceLat;
    private double sourceLng;
    private double destLat;
    private double destLng;
    private double distanceKm;
    private double trafficEtaMin;
    private String trafficLevel;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
