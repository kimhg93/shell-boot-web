package com.boot.shell.common.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
    private String userId;
    private String originalFileName;
    private String saveFileName;
    private Long fileSize;
}
