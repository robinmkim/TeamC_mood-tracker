package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.MediaDto;
import com.teamc.moodtracker.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/post")
public class BoardController {
    @Autowired
    private BoardService service;

    private final static String imageDirectory = Paths.get("").toAbsolutePath() + "/uploads/";
    @PostMapping("/add")
    public int addBoardContent(@ModelAttribute BoardDto dto,
                               @RequestParam("mediaList") List<MultipartFile> mediaList) {
        List<MediaDto> mediaDtos = new ArrayList<>();

        for (MultipartFile multipartFile : mediaList) {
            MediaDto mediaDto = new MediaDto();
            mediaDto.setMd_name(multipartFile.getOriginalFilename());
            mediaDto.setMd_path(imageDirectory);
            mediaDto.setMd_type(multipartFile.getContentType());
            mediaDtos.add(mediaDto);
            String filePath = imageDirectory + multipartFile.getOriginalFilename();
            System.out.println(filePath);
            uploadFile(multipartFile, filePath);
        }

        service.addBoardContent(dto, mediaDtos);

        // 성공 시 적절한 상태 코드 반환
        return 1; // 예시: 성공 시 1 반환
    }

    public String uploadFile(MultipartFile file, String path){
        if(!file.isEmpty()){
            try (FileOutputStream writer = new FileOutputStream(path)) {
                writer.write(file.getBytes());
                return "File Uploaded Successfully!";
            } catch (Exception e) {
                log.error("Error while uploading file " + file.getOriginalFilename(), e);
                return "Error while uploading file" + file.getOriginalFilename();
            }
        } else {
            return "No File Selected!";
        }
    }
    private String getExtension(MultipartFile multipartFile) {
        // 업로드 된 파일의 이름을 변수에 저장
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.indexOf(".");
        if (index > -1) {
            return fileName.substring(index);
        }
        return "";
    }

}
