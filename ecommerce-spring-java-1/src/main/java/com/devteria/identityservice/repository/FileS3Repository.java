package com.devteria.identityservice.repository;

import com.devteria.identityservice.entity.FileS3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileS3Repository extends JpaRepository<FileS3, Long> {

}
