package com.teamc.moodtracker.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
@Slf4j
public class FileUpload {
    public static String uploadFile(MultipartFile file, String path) {
        if (!file.isEmpty()) {
            try (FileOutputStream writer = new FileOutputStream(path)) {
                writer.write(file.getBytes());
                return "File Uploaded Successfully!";
            } catch (Exception e) {
                log.error("Error while uploading file " + file.getOriginalFilename(), e);
                return "Error while uploading file " + file.getOriginalFilename();
            }
        } else {
            return "No File Selected!";
        }
    }
}
