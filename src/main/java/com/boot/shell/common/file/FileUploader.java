package com.boot.shell.common.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileUploader {

    private final static String SAVE_DIR = Paths.get("C", "uploadFiles").toString();

    public void fileUpload(MultipartFile[] uploadFiles) throws IOException {
        for(MultipartFile uploadFile : uploadFiles){
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String fileName = uploadFile.getOriginalFilename() + UUID.randomUUID() +"_"+ dateTime ;

            File file = new File(SAVE_DIR + fileName);

            uploadFile.transferTo(file);

            FileDto fileDto = new FileDto();
            fileDto.setFileSize(uploadFile.getSize());
            fileDto.setOriginalFileName(uploadFile.getOriginalFilename());
            fileDto.setSaveFileName(fileName);

            // 업로드 정보 저장....  save(fileDto)
        }
    }
}