package com.trafficnavigator.traffic_backend.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OsrmClient {
    public final RestTemplate restTemplate = new RestTemplate();
    public String getRoutes(double slat, double slng, double dlat, double dlng) {
        String url = String.format(
                "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&geometries=geojson&alternatives=true",
                slng, slat, dlng, dlat
        );
        return restTemplate.getForObject(url, String.class);
    }
}
