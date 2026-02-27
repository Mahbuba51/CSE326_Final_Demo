package com.foodibd.backend.repository;

import com.foodibd.backend.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Integer> {
}
