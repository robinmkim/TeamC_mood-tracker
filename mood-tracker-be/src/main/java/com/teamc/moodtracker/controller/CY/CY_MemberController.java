package com.teamc.moodtracker.controller.CY;

import com.teamc.moodtracker.dto.CY.CY_MemberDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.CY.CY_MemberService;
import com.teamc.moodtracker.util.FileUpload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/member")
public class CY_MemberController {

    @Autowired
    private CY_MemberService cmemberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final String imageDirectory = "src/main/resources/static/images/";

    @GetMapping("/info/{memberId}")
    public CY_MemberDto getMemberInfo_board(@PathVariable("memberId") int memberId) {
        System.out.println(memberId);

        return cmemberService.getMemberInfo_board(memberId);
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
        cmemberService.updateProfileImage(dto);
        return "프로필 사진 변경 성공";
    }

    @PutMapping("/profile/name")
    public String updateProfileName(@AuthenticationPrincipal MemberDto dto,
                                 @RequestBody Map<String, String> m_name) {
        dto.setM_name(m_name.get("m_name"));
        cmemberService.updateProfileName(dto);
        return "닉네임 변경 성공";
    }

    @PutMapping("/profile/password")
    public String updateProfilePassword(@AuthenticationPrincipal MemberDto dto,
                                     @RequestBody Map<String, String> m_password) {
        dto.setM_pwd(passwordEncoder.encode(m_password.get("m_pwd")));
        cmemberService.checkPassword(dto);
        dto.setM_pwd(m_password.get("new_pwd"));
        cmemberService.updateProfilePassword(dto);
        return "비밀번호 변경 성공";
    }

    @PutMapping("/profile/handle")
    public String updateProfileHandle(@AuthenticationPrincipal MemberDto dto,
                                   @RequestBody Map<String, String> m_handle) {
        dto.setM_handle(m_handle.get("m_handle"));
        cmemberService.updateProfileHandle(dto);
        return "핸들 변경 성공";
    }

    @PutMapping("/profile/gender")
    public String updateProfileGender(@AuthenticationPrincipal MemberDto dto,
                                      @RequestBody Map<String, String> m_gender) {
        dto.setM_gender(m_gender.get("m_gender"));
        cmemberService.updateProfileGender(dto);
        return "성별 변경 성공";
    }

    @PutMapping("/profile/bio")
    public String updateProfileBio(@AuthenticationPrincipal MemberDto dto,
                                      @RequestBody Map<String, String> m_bio) {
        dto.setM_bio(m_bio.get("m_bio"));
        cmemberService.updateProfileBio(dto);
        return "자기소개 변경 성공";
    }
}

