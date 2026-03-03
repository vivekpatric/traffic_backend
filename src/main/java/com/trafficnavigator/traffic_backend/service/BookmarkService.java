package com.trafficnavigator.traffic_backend.service;

import com.trafficnavigator.traffic_backend.auth.User;
import com.trafficnavigator.traffic_backend.auth.UserContextService;
import com.trafficnavigator.traffic_backend.dto.BookmarkDto;
import com.trafficnavigator.traffic_backend.model.Bookmark;
import com.trafficnavigator.traffic_backend.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository repository;
    private final UserContextService userContextService;

    public void save(BookmarkDto dto){
        User user = userContextService.getCurrentUser();
        Bookmark b = new Bookmark();
        b.setUser(user);
        b.setSourceLat(dto.sourceLat);
        b.setSourceLng(dto.sourceLng);
        b.setDestLat(dto.destLat);
        b.setDestLng(dto.destLng);
        b.setDistanceKm(dto.distanceKm);
        b.setTrafficEtaMin(dto.trafficEtaMin);
        b.setTrafficLevel(dto.trafficLevel);
        b.setCreatedAt(LocalDateTime.now());

        repository.save(b);
    }
    public List<Bookmark> getAll(){
        User user = userContextService.getCurrentUser();
        return repository.findByUser(user);
    }

}
