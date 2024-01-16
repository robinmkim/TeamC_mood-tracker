package com.teamc.moodtracker.controller.CY;

import com.teamc.moodtracker.dto.CY.CY_MemberDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.CY.CY_MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/member")
public class CY_MemberController {
    @Autowired
    private CY_MemberService cmemberService;

    @GetMapping("/myInfo")
    public CY_MemberDto getMemberInfo_board(@AuthenticationPrincipal MemberDto memberDto) {
        int mid = memberDto.getM_id();
        System.out.println("----member controller userInfo----");
        System.out.println("member num : " + mid);

        return cmemberService.getMemberInfo_board(mid);


    }
}
