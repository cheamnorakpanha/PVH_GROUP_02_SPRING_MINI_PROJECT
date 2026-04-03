package org.me.pvh_group_02_spring_mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileMetaData {
    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long fileSize;
}