package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.MediaDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.MemberService;
import com.teamc.moodtracker.util.FileUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("api/auth/member")
public class MemberController {
    @Autowired
    MemberService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    String imageDirectory = "src/main/resources/static/images/";

    @PostMapping("/signUp")
    public int signUpTest(@ModelAttribute MemberDto dto,
            @RequestParam(value = "m_profile", required = false) MultipartFile m_profile) {

        if (m_profile != null) {
            dto.setM_img_name(m_profile.getOriginalFilename());
            dto.setM_img_path("images/");
            String filePath = imageDirectory + m_profile.getOriginalFilename();
            System.out.println(filePath);
            FileUpload.uploadFile(m_profile, filePath);
        }

        dto.setM_pwd(passwordEncoder.encode(dto.getM_pwd()));
        System.out.println(dto.toString());
        service.addMember(dto);
        return 1; // 예시: 성공 시 1 반환
    }
}
