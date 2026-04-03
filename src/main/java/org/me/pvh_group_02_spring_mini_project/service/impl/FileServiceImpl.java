package org.me.pvh_group_02_spring_mini_project.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.me.pvh_group_02_spring_mini_project.model.response.FileMetaData;
import org.me.pvh_group_02_spring_mini_project.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final S3Client s3Client;

    @Value("${rustfs.bucket.name}")
    private String bucketName;

    public void createBucketIfNotExists() {
        try {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
        } catch (NoSuchBucketException e) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        }
    }

    @SneakyThrows
    @Override
    public FileMetaData uploadFile(MultipartFile file) {
        createBucketIfNotExists();

        String originalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFileName);
        String contentType = file.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .contentType(contentType)
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to RustFS", e);
        }

        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/preview-file/" + fileName)
                .toUriString();

        return FileMetaData.builder()
                .fileName(fileName)
                .fileType(file.getContentType())
                .fileUrl(fileUrl)
                .fileSize(file.getSize())
                .build();
    }

    @SneakyThrows
    @Override
    public Resource getFileByFileName(String key) {
        try {
            InputStream inputStream = s3Client.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build()
            );
            return new InputStreamResource(inputStream);
        } catch (NoSuchKeyException e) {
            throw new RuntimeException("File not found: " + key, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from RustFS", e);
        }
    }
}