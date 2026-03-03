package com.trafficnavigator.traffic_backend.controller;


import com.trafficnavigator.traffic_backend.dto.RouteHistoryDto;
import com.trafficnavigator.traffic_backend.model.RouteHistory;
import com.trafficnavigator.traffic_backend.service.RouteHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor

public class RouteHIstoryController {
    private final RouteHistoryService service;

    @PostMapping
    public void save(@RequestBody RouteHistoryDto dto){
        service.save(dto);
    }
    @GetMapping
    public List<RouteHistory> getAll(){
        return service.getAll();
    }

}
