package com.trafficnavigator.traffic_backend.service;

import com.trafficnavigator.traffic_backend.client.OsrmClient;
import com.trafficnavigator.traffic_backend.dto.RouteOptionDto;
import com.trafficnavigator.traffic_backend.dto.RouteOptionsRequestDto;
import com.trafficnavigator.traffic_backend.dto.RouteOptionsResponseDto;
import com.trafficnavigator.traffic_backend.dto.RouteResponseDto;
import com.trafficnavigator.traffic_backend.traffic.TrafficEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private final OsrmClient osrmClient;
    private final TrafficEvaluator trafficEvaluator;
    private final ObjectMapper mapper = new ObjectMapper();

    public RouteService(OsrmClient osrmClient, TrafficEvaluator trafficEvaluator) {
        this.osrmClient = osrmClient;
        this.trafficEvaluator = trafficEvaluator;
    }

    public RouteOptionsResponseDto getRouteOptions(
            double slat, double slng,
            double dlat, double dlng) throws Exception {

        String response = osrmClient.getRoutes(slat, slng, dlat, dlng);
        JsonNode root = mapper.readTree(response);
        JsonNode routes = root.path("routes");

        List<RouteOptionDto> options = new ArrayList<>();

        for (int i = 0; i < Math.min(3, routes.size()); i++) {
            JsonNode r = routes.get(i);

            double baseEta = r.path("duration").asDouble() / 60;
            double trafficEta = baseEta * (1.1 + Math.random() * 0.4);

            RouteOptionDto dto = new RouteOptionDto();
            dto.routeId = "R" + (i + 1);
            dto.distanceKm = r.path("distance").asDouble() / 1000;
            dto.baseEtaMin = baseEta;
            dto.trafficEtaMin = trafficEta;
            dto.trafficLevel = trafficEvaluator.classify(baseEta, trafficEta);
            dto.geometry = r.path("geometry");

            options.add(dto);
        }
        System.out.println("Total routes from OSRM: " + routes.size());

        RouteOptionsResponseDto resp = new RouteOptionsResponseDto();
        resp.routes = options;
        return resp;
    }

}
