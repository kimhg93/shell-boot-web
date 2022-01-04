package com.boot.shell.common;

import com.boot.shell.common.file.FileUploader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class FIleUploadTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void fileUploadTest() throws IOException {
        String fileName = "TestMemo";
        String contentType = "txt";
        String filePath = "src/TestMemo.txt";

        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(fileName,fileName + "." + contentType ,contentType, fileInputStream);

        List<MultipartFile> files = new ArrayList<>();
        files.add(mockMultipartFile);

        new FileUploader().upload(files);
    }
}
