package com.trafficnavigator.traffic_backend.service;


import com.trafficnavigator.traffic_backend.kafka.event.RouteSelectedEvent;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class TrafficAnalyticsService {
    static class HotSpotsData{
        AtomicInteger count = new AtomicInteger(0);
        long lastUpdated;
    }
    public final ConcurrentHashMap<String, AtomicInteger> trafficCounter = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String,HotSpotsData> hotspotMap= new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String,AtomicInteger> hourlyTraffic = new ConcurrentHashMap<>();
    public void processEvent(RouteSelectedEvent event){
        int hour = LocalDateTime.now().getHour();
        String destKey =
                round(event.getDestLat())+"_"+
                round(event.getDestLng());
        String hourlyKey = destKey+"_H"+hour;
        hourlyTraffic
                .computeIfAbsent(hourlyKey,k-> new AtomicInteger(0))
                        .incrementAndGet();

        trafficCounter
                .computeIfAbsent(event.getTrafficLevel(),k -> new AtomicInteger(0))
                .incrementAndGet();
        int count =trafficCounter.get(event.getTrafficLevel()).get();
        System.out.println("TrafficAnalytics: "
                              +event.getTrafficLevel()
                              +"count:"+count);
        String routeKey = round(event.getSourceLat()) + "_" +
                          round(event.getSourceLng()) + "_" +
                          round(event.getDestLat()) + "_"+
                          round(event.getDestLng());
        hotspotMap.compute(routeKey,(key,data)->{
            if(data == null){
                data = new HotSpotsData();
            }
            data.count.incrementAndGet();
            data.lastUpdated=System.currentTimeMillis();
            return data;
        });
        int hotspotCount = hotspotMap.get(routeKey).count.get();
        System.out.println("AI Route Key"+routeKey+"usage"+hotspotCount);


        if("Red".equalsIgnoreCase(event.getTrafficLevel()) && hotspotCount>=3){
            System.out.println("AI Alert : Possible Traffic Hotspot Detected near RouteKey"+routeKey+"usage"+hotspotCount);
        }
    }
    private double round(double value){
        return Math.round(value*1000.0)/1000.0;
    }
    public Map<String,Integer>getHotspots(){
        long now = System.currentTimeMillis();
        long TTL = 60000;
        return hotspotMap.entrySet().stream()
                .filter(e -> now -e.getValue().lastUpdated < TTL)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e ->e.getValue().count.get()
                ));
    }

    public Map<String,Integer> getTrafficCounts(){
        return trafficCounter.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().get()
                ));
    }
    public String predictNextTenMinutes(double destLat,double destLng){
        String destKey =
                round(destLat)+"_"+
                        round(destLng);
        int currentHour =LocalDateTime.now().getHour();
        String hourlyKey =destKey+"_H"+currentHour;
        int historicalCount =hourlyTraffic.getOrDefault(hourlyKey,new AtomicInteger(0)).get();
        if(historicalCount>=8){
            return "High Chance of Traffic in next 10 minutes";
        } else if (historicalCount>=4) {
            return "Moderate Congestion soon";
        }else {
            return "Traffic is expected to be normal as of now";
        }
    }
}
