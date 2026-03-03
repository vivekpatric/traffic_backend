package com.trafficnavigator.traffic_backend.service;


import com.trafficnavigator.traffic_backend.auth.User;
import com.trafficnavigator.traffic_backend.auth.UserContextService;
import com.trafficnavigator.traffic_backend.auth.UserRepository;
import com.trafficnavigator.traffic_backend.dto.RouteHistoryDto;
import com.trafficnavigator.traffic_backend.kafka.RouteEventProducer;
import com.trafficnavigator.traffic_backend.kafka.event.RouteSelectedEvent;
import com.trafficnavigator.traffic_backend.model.RouteHistory;
import com.trafficnavigator.traffic_backend.repository.RouteHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteHistoryService {

    private final RouteHistoryRepository repository;
    private final UserContextService userContextService;
    private final Optional<RouteEventProducer> producer;
    public void save(RouteHistoryDto dto){

        User user = userContextService.getCurrentUser();

        RouteSelectedEvent event = RouteSelectedEvent.builder()
                        .email(user.getEmail())
                                .sourceLat(dto.sourceLat)
                                        .sourceLng(dto.sourceLng)
                                                .destLat(dto.destLat)
                                                        .destLng(dto.destLng)
                                                                .trafficEtaMin(dto.trafficEtaMin)
                                                                        .trafficLevel(dto.trafficLevel)
                                                                                .timestamp(System.currentTimeMillis())
                                                                                        .build();
        producer.ifPresent(p -> p.publishRouteSelected(event));
        System.out.println("Event Sent instead of direct DB save");
    }
    public List<RouteHistory> getAll(){

        User user = userContextService.getCurrentUser();
        return repository.findByUser(user);
    }

}
