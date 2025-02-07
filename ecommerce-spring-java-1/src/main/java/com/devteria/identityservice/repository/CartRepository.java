package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
