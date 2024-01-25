package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.MemberService;
import com.teamc.moodtracker.util.FileUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

//    String imageDirectory = "src/main/resources/static/images/";
    String imageDirectory = "build/resources/main/static/images/";

    @GetMapping("/userInfo/memberHandle") // 윤영호 postWrite_yh에서 닉네임 가져올 때 사용.
    public String getMemberHandle(@AuthenticationPrincipal MemberDto memberDto) {
        return memberDto.getM_handle();
    }

    @GetMapping("/search")
    public List<MemberDto> SearchMember(@RequestParam String memberName) {
        List<MemberDto> searchMembers = memberService.SearchMember(memberName);
        return searchMembers;
    }

    @GetMapping("/info/{memberId}")
    public MemberDto getMemberInfo_board(@PathVariable("memberId") int memberId) {
        System.out.println(memberId);

        return memberService.getMemberInfo_board(memberId);
    }

    @PutMapping("/profile/image")
    public String getProfileImage(@AuthenticationPrincipal MemberDto dto,
            @RequestParam(value = "m_profile", required = true) MultipartFile m_profile) {
        int memberId = dto.getM_id();

        if (m_profile != null) {
            dto.setM_img_name(m_profile.getOriginalFilename());
            dto.setM_img_path("images/");
            String filePath = imageDirectory + m_profile.getOriginalFilename();
            FileUpload.uploadFile(m_profile, filePath);
        }
        memberService.updateProfileImage(dto);
        return "프로필 사진 변경 성공";
    }

    @PutMapping("/profile/name")
    public String updateProfileName(@AuthenticationPrincipal MemberDto dto,
            @RequestBody Map<String, String> m_name) {
        dto.setM_name(m_name.get("m_name"));
        memberService.updateProfileName(dto);
        return "닉네임 변경 성공";
    }

    @PutMapping("/profile/password")
    public String updateProfilePassword(@AuthenticationPrincipal MemberDto dto,
            @RequestBody Map<String, String> m_password) {
        try {
            dto.setM_pwd(passwordEncoder.encode(m_password.get("m_pwd")));
            memberService.checkPassword(dto);
        } catch (IllegalArgumentException e) {
            return "비밀번호가 일치하지 않습니다.";
        }
        dto.setM_pwd(m_password.get("new_pwd"));
        memberService.updateProfilePassword(dto);
        return "비밀번호 변경 성공";
    }

    @PutMapping("/profile/handle")
    public String updateProfileHandle(@AuthenticationPrincipal MemberDto dto,
            @RequestBody Map<String, String> m_handle) {
        dto.setM_handle(m_handle.get("m_handle"));
        memberService.updateProfileHandle(dto);
        return "핸들 변경 성공";
    }

    @PutMapping("/profile/gender")
    public String updateProfileGender(@AuthenticationPrincipal MemberDto dto,
            @RequestBody Map<String, String> m_gender) {
        dto.setM_gender(m_gender.get("m_gender"));
        memberService.updateProfileGender(dto);
        return "성별 변경 성공";
    }

    @PutMapping("/profile/bio")
    public String updateProfileBio(@AuthenticationPrincipal MemberDto dto,
            @RequestBody Map<String, String> m_bio) {
        dto.setM_bio(m_bio.get("m_bio"));
        memberService.updateProfileBio(dto);
        return "자기소개 변경 성공";
    }

}
