package com.project3.project3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    private final S3Client s3Client;
    private final String region;

    @Autowired
    public ImageService(S3Client s3Client) {
        this.s3Client = s3Client;
        this.region = System.getenv("AWS_REGION");
    }

    public String uploadImage(MultipartFile file, String bucketName, String folderName) throws IOException {
        String objectKey = folderName + "/" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return objectKey;
    }
}



