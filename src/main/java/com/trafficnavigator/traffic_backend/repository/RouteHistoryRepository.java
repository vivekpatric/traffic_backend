package com.trafficnavigator.traffic_backend.repository;

import com.trafficnavigator.traffic_backend.auth.User;
import com.trafficnavigator.traffic_backend.model.RouteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteHistoryRepository extends JpaRepository<RouteHistory,Long> {
    List<RouteHistory> findByUser(User user);
}
