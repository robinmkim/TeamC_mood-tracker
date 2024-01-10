package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.MediaDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.BoardService;
import com.teamc.moodtracker.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    String imageDirectory = "src/main/resources/static/images/";
    @PostMapping("/add")
    public int addBoardContent(@AuthenticationPrincipal MemberDto memberDto,
                               @ModelAttribute BoardDto dto,
                               @RequestParam(value = "mediaList", required = false) List<MultipartFile> mediaList) {
        List<MediaDto> mediaDtos = new ArrayList<>();
        dto.setM_id(memberDto.getM_id());
        if(mediaList != null) {
            for (MultipartFile multipartFile : mediaList) {
                MediaDto mediaDto = new MediaDto();
                mediaDto.setMd_name(multipartFile.getOriginalFilename());
                mediaDto.setMd_path("images/");
                mediaDto.setMd_type(multipartFile.getContentType());
                mediaDtos.add(mediaDto);
                String filePath = imageDirectory + multipartFile.getOriginalFilename();
                System.out.println(filePath);
                uploadFile(multipartFile, filePath);
            }
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

    @GetMapping("/get/{b_id}")
    public BoardDto getBoardDetail(@PathVariable int b_id) {
        return service.getBoardDetail(b_id);
    }

    @GetMapping("/list")
    public List<Integer> getBoardList(@RequestParam(value="lastRowNum") int lastRowNum) {
        return service.getBoardList(lastRowNum);
    }
}
