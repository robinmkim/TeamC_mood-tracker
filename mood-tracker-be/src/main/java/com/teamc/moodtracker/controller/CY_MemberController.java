package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.service.CY_MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/member")
public class CY_MemberController {
    @Autowired
    private CY_MemberService cmemberService;

//    @GetMapping("/userInfo/{mid}")
//    public CY_MemberDto getMemberInfo_board(@PathVariable int mid) {
//        System.out.println("----member controller userInfo----");
//        System.out.println("member num : " + mid);
//        return cmemberService.getMemberInfo_board(mid);
//    }
}
