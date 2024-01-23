package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.ReplyDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.ReplyLikeDto;
import com.teamc.moodtracker.service.NotificationService;
import com.teamc.moodtracker.service.ReplyLikeService;
import com.teamc.moodtracker.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/jh_reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyLikeService likeService;


    @Autowired
    private NotificationService notificationService; // 윤영호

    @Transactional
    @GetMapping("/getReplyList")
    public List<ReplyDto> getReplyLis(@AuthenticationPrincipal MemberDto memberDto,
                                      @RequestParam(value = "cm_id") int cm_id) {
        List<ReplyDto> replys = replyService.getReplyLis(cm_id);
        ReplyLikeDto dto = new ReplyLikeDto();
        dto.setM_id(memberDto.getM_id());
        boolean isMyLike = false;
        for (ReplyDto reply : replys) {
            dto.setRe_id(reply.getRe_id());
            isMyLike = likeService.isMyLikeReply(dto);
            reply.setIsMyLike(isMyLike);

        }
        return replys;
    }

    ;

    @PostMapping("/addReply")
    public int addReply(@AuthenticationPrincipal MemberDto memberDto,
            @ModelAttribute ReplyDto dto) {
        dto.setM_id(memberDto.getM_id());
        System.out.println("!!!");
        System.out.println(dto);
        replyService.addReply(dto);

        notificationService.addReply_SaveNotificationAndSendAlert(dto); // 알림/ 윤영호

        return 1;
    }

    ;

    @Transactional
    @GetMapping("/delReply")
    public void delReply(@RequestParam(value = "re_id") int re_id) {
        replyService.delReply(re_id);
    }


    @GetMapping("/getRe_idList")
    public List<Integer> getRe_idList(@RequestParam("cm_id") int cm_id) {

        return replyService.getRe_idList(cm_id);
    }

    @Transactional
    @GetMapping("/getReplyDetail")
    public ReplyDto getReplyDetail(@AuthenticationPrincipal MemberDto memberDto, @RequestParam("re_id") int re_id) {
        ReplyDto reply = replyService.getReplyDetail(re_id);
        ReplyLikeDto dto = new ReplyLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setRe_id(re_id);
        reply.setIsMyLike(likeService.isMyLikeReply(dto));
        reply.setLikeCount(likeService.replyLikeCount(re_id));
        return reply;
    }

}
