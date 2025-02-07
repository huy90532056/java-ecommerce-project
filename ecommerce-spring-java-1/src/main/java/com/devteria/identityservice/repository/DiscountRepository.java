package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
