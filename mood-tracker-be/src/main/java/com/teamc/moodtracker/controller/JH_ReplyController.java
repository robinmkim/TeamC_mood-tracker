package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.JH_ReplyDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.Reply_LikeDto;
import com.teamc.moodtracker.service.JH_ReplyLikeService;
import com.teamc.moodtracker.service.JH_ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/jh_reply")
public class JH_ReplyController {

    @Autowired
    private JH_ReplyService replyService;

    @Autowired
    private JH_ReplyLikeService likeService;

    @Transactional
    @GetMapping("/getReplyList")
    public List<JH_ReplyDto> getReplyLis(@AuthenticationPrincipal MemberDto memberDto,@RequestParam(value = "cm_id") int cm_id) {
        System.out.println(cm_id);
        List<JH_ReplyDto> replys = replyService.getReplyLis(cm_id);
        Reply_LikeDto dto = new Reply_LikeDto();
        dto.setM_id(memberDto.getM_id());
        boolean isMyLike = false;
        for (JH_ReplyDto reply : replys) {
            dto.setRe_id(reply.getRe_id());
            isMyLike = likeService.isMyLikeReply(dto);
            reply.setIsMyLike(isMyLike);

        }
        return replys;
    }

    ;

    @PostMapping("/addReply")
    public int addReply(@AuthenticationPrincipal MemberDto memberDto,
                        @ModelAttribute JH_ReplyDto dto) {
        dto.setM_id(memberDto.getM_id());
        System.out.println("!!!");
        System.out.println(dto);
        replyService.addReply(dto);
        return 1;
    }

    ;

    @Transactional
    @GetMapping("/delReply")
    public void delReply(@AuthenticationPrincipal MemberDto memberDto,@RequestParam(value = "re_id") int re_id) {
        Reply_LikeDto dto = new Reply_LikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setRe_id(re_id);
        likeService.delReplyLike(likeService.getRelike_id(dto));
        replyService.delReply(re_id);
    }


}