package com.trafficnavigator.traffic_backend.repository;

import com.trafficnavigator.traffic_backend.auth.User;
import com.trafficnavigator.traffic_backend.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    List<Bookmark>findByUser(User user);
}
