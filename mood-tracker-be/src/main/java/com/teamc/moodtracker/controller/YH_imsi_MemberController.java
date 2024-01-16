package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/member")
public class YH_imsi_MemberController {
    @Autowired
    MemberService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    String imageDirectory = "src/main/resources/static/images/";


    @GetMapping("/userInfo/{mid}") //윤영호
    public MemberDto getMemberInfo_board(@AuthenticationPrincipal MemberDto memberDto, @PathVariable int mid) {
        System.out.println("----member controller userInfo----");
        mid = memberDto.getM_id();
        System.out.println("member num mid : " + mid);
        return service.getMemberInfo_board(mid);
    }

    @GetMapping("/userInfo/memberHandle") //윤영호 postWrite_yh에서 본인 닉네임(m_handle) 가져올 때 사용.
    public  String getMemberHandle(@AuthenticationPrincipal MemberDto memberDto){
        return memberDto.getM_handle();
    }
    
//    @GetMapping("/profileImg") //윤영호 :: 프로필이미지 로딩용 :: 임시
//    public String getPrifileImg(@AuthenticationPrincipal MemberDto memberDto, @RequestParam int m_id) throws IOException {
//        String encodedString = new String();
//        String imagePath = imageDirectory + memberDto.getM_img_name();
//        System.out.println(imagePath);
//        File file = new File(imagePath); // 파일 열기
//        if(file.exists()){ // 파일이 존재하는지 검사
//            System.out.println("File exists.");
//            byte[] fileContent = FileCopyUtils.copyToByteArray(file); // file을 byte로 변경
//            encodedString = Base64.getEncoder().encodeToString(fileContent); // byte를 base64로  인코딩
//        }
//        System.out.println(encodedString);
//        return encodedString;
//    }

}
