package org.me.pvh_group_02_spring_mini_project.service;

import org.me.pvh_group_02_spring_mini_project.model.response.FileMetaData;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileMetaData uploadFile(MultipartFile file);

    Resource getFileByFileName(String fileName);
}
