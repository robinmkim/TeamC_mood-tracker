package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.JH_ReplyDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.JH_ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/jh_reply")
public class JH_ReplyController {

    @Autowired
    private JH_ReplyService replyService;

    @GetMapping("/getReplyList")
    public List<JH_ReplyDto> getReplyLis(@RequestParam(value = "cm_id") int cm_id) {
        System.out.println(cm_id);
      return replyService.getReplyLis(cm_id);
    };

    @PostMapping("/addReply")
    public int addReply(@AuthenticationPrincipal MemberDto memberDto,
                        @ModelAttribute JH_ReplyDto dto){
        dto.setM_id(memberDto.getM_id());
        System.out.println("!!!");
        System.out.println(dto);
      replyService.addReply(dto);
        return 1;
    };


}
