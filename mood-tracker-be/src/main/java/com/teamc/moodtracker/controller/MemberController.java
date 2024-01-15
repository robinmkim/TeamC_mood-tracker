package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.CY_MemberDto;
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
public class MemberController {
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

    @GetMapping("/userInfo/memberHandle") //윤영호
    public  String getMemberHandle(@AuthenticationPrincipal MemberDto memberDto){
        return memberDto.getM_handle();
    }

}
