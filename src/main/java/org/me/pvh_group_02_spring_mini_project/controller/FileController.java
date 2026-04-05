package org.me.pvh_group_02_spring_mini_project.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.me.pvh_group_02_spring_mini_project.model.response.ApiResponse;
import org.me.pvh_group_02_spring_mini_project.model.response.FileMetaData;
import org.me.pvh_group_02_spring_mini_project.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    @Value("${spring.file-upload-path}")
    private String pathName;

    @PostMapping(value = "upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileMetaData>> uploadFile(@RequestParam MultipartFile file) {
        FileMetaData fileMetaData = fileService.uploadFile(file);
        ApiResponse<FileMetaData> response = ApiResponse.<FileMetaData>builder()
                .success(true)
                .message("File upload successfully to RustFS")
                .status(HttpStatus.CREATED.name())
                .payload(fileMetaData)
                .timestamp(Instant.now()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("preview-file/{file-name}")
    public ResponseEntity<Resource> getFileByFileName(@PathVariable("file-name") String fileName) {
        Resource resource = fileService.getFileByFileName(fileName);



        String contentType = MediaTypeFactory
                .getMediaType(fileName)
                .map(MediaType::toString)
                .orElse(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(contentType)).body(resource);
    }
}







