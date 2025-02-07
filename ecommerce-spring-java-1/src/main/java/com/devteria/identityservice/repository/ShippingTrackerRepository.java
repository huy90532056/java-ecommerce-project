package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.ShippingTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingTrackerRepository extends JpaRepository<ShippingTracker, Long> {
}
