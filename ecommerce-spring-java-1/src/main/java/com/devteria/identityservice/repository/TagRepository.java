package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {

    // Tìm Tag theo tên
    Optional<Tag> findByTagName(String tagName);
    boolean existsByTagName(String tagName);
}