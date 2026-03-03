package com.trafficnavigator.traffic_backend.controller;

import com.trafficnavigator.traffic_backend.dto.BookmarkDto;
import com.trafficnavigator.traffic_backend.model.Bookmark;
import com.trafficnavigator.traffic_backend.service.BookmarkService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor

public class BookmarkController {
    private final BookmarkService service;

    @PostMapping
    public void save(@RequestBody BookmarkDto dto){
        service.save(dto);
    }
    @GetMapping
    public List<Bookmark> getAll(){
        return service.getAll();
    }
}

