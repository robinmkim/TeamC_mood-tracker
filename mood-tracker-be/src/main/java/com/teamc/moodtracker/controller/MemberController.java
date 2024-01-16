package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    String imageDirectory = "src/main/resources/static/images/";

    @GetMapping("/userInfo/memberHandle") //윤영호 postWrite_yh에서 닉네임 가져올 때 사용.
    public  String getMemberHandle(@AuthenticationPrincipal MemberDto memberDto){
        return memberDto.getM_handle();
    }

}
