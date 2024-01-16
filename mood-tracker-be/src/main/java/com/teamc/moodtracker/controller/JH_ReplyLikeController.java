package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.Board_LikeDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.Reply_LikeDto;
import com.teamc.moodtracker.service.JH_BoardLikeService;
import com.teamc.moodtracker.service.JH_ReplyLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/jh_ReplyLike")
public class JH_ReplyLikeController {
    @Autowired
    private JH_ReplyLikeService service;

//    @GetMapping("/isMylike")
//    public boolean isMyLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("b_id")int b_id){
//        Board_LikeDto dto = new Board_LikeDto();
//        dto.setM_id(memberDto.getM_id());
//        dto.setB_id(b_id);
//        return service.isMyLike(dto);
//    }

//    @GetMapping("/getBlike_id")
//    public int getBlike_id(@ModelAttribute Board_LikeDto dto){
//        System.out.println("???");
//
//        return service.getBlike_id(dto);
//    }

    @GetMapping("/delReplyLike")
    public void delReplyLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("re_id")int re_id){
        Reply_LikeDto dto = new Reply_LikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setRe_id(re_id);
        service.delReplyLike(service.getRelike_id(dto));
    }

    @GetMapping("/addReplyLike")
    public int addReplyLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("re_id")int re_id) {
        System.out.println("addReplyLike");
        Reply_LikeDto dto = new Reply_LikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setRe_id(re_id);
        System.out.println("re_id: "+dto.getRe_id());
        return service.addReplyLike(dto);
    }

}
