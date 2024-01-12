package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.JH_MemberDto;
import com.teamc.moodtracker.service.JH_MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/member")
public class JH_MemberController {
    @Autowired
    private JH_MemberService service;
    String imageDirectory = "src/main/resources/static/images/";

    @GetMapping("/userInfo/{m_id}")
    public JH_MemberDto getMemberInfo_board(@PathVariable int m_id) {
        return service.getMemberInfo_board(m_id);
    }
}
