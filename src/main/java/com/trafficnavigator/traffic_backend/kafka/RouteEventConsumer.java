package com.trafficnavigator.traffic_backend.kafka;

import com.trafficnavigator.traffic_backend.auth.User;
import com.trafficnavigator.traffic_backend.auth.UserRepository;
import com.trafficnavigator.traffic_backend.kafka.event.RouteSelectedEvent;
import com.trafficnavigator.traffic_backend.model.RouteHistory;
import com.trafficnavigator.traffic_backend.repository.RouteHistoryRepository;
import com.trafficnavigator.traffic_backend.service.RouteHistoryService;
import com.trafficnavigator.traffic_backend.service.TrafficAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@ConditionalOnProperty(
        name = "kafka.enabled",
        havingValue = "true",
        matchIfMissing = false
)
@Service
@RequiredArgsConstructor
public class RouteEventConsumer {
    private final TrafficAnalyticsService analyticsService;
    private final RouteHistoryRepository historyRepository;
    private final UserRepository  userRepository;
    @KafkaListener(topics = "route-events",groupId = "traffic-group")
    public void listen(RouteSelectedEvent event){
        System.out.println("Kafka Event Recieved: " + event.getEmail() +"traffic" +event.getTrafficLevel());
        analyticsService.processEvent(event);
        User user =userRepository.findByemail(event.getEmail()).orElse(null);
        if(user == null){
            System.out.println("User not found");
            return;
        }
        RouteHistory history = new RouteHistory();
        history.setUser(user);
        history.setSourceLat(event.getSourceLat());
        history.setSourceLng(event.getSourceLng());
        history.setDestLat(event.getDestLat());
        history.setDestLng(event.getDestLng());
        history.setDistanceKm(event.getDistanceKm());
        history.setDurationMin(event.getDurationMin());
        history.setTrafficEtaMin(event.getTrafficEtaMin());
        history.setTrafficLevel(event.getTrafficLevel());
        history.setCreatedAt(LocalDateTime.now());

        historyRepository.save(history);
        System.out.println("Async DB save completed");
    }



}
