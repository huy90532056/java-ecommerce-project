package com.devteria.identityservice.service;

import com.devteria.identityservice.entity.FileS3;
import com.devteria.identityservice.repository.FileS3Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileS3Service {

    private final S3Service s3Service;
    private final FileS3Repository fileS3Repository;
    public FileS3 uploadFile(MultipartFile multipartFile, String name) {
        // Upload file to AWS S3
        String fileUrl = s3Service.uploadFile(multipartFile);
        FileS3 fileS3 = new FileS3();
        fileS3.setFileUrl(fileUrl);
        fileS3.setName(name);
        // Save video to database
        fileS3Repository.save(fileS3);
        return fileS3;
    }

    public List<FileS3> getAllFiles() {
        // Retrieve all files from the database
        return fileS3Repository.findAll();
    }
}
