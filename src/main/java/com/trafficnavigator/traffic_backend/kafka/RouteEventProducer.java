package com.trafficnavigator.traffic_backend.kafka;

import com.trafficnavigator.traffic_backend.kafka.event.RouteSelectedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@ConditionalOnProperty(
        name = "kafka.enabled",
        havingValue = "true",
        matchIfMissing = false
)
@Service
@RequiredArgsConstructor
public class RouteEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publishRouteSelected(RouteSelectedEvent event){
        try{
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("route-events",json);
            System.out.println("Kafka Event Send: "+event.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
