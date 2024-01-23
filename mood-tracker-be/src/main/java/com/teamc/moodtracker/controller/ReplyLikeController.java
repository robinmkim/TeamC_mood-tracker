package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.ReplyLikeDto;
import com.teamc.moodtracker.service.NotificationService;
import com.teamc.moodtracker.service.ReplyLikeService;
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
public class ReplyLikeController {
    @Autowired
    private ReplyLikeService service;

    @Autowired
    private NotificationService notificationService; // 알림

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
        ReplyLikeDto dto = new ReplyLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setRe_id(re_id);
        service.delReplyLike(service.getRelike_id(dto));
    }

    @GetMapping("/addReplyLike")
    public int addReplyLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("re_id")int re_id) {
        System.out.println("addReplyLike");
        ReplyLikeDto dto = new ReplyLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setRe_id(re_id);
        System.out.println("re_id: "+dto.getRe_id());

        notificationService.addReplyLike_SaveNotificationAndSendAlert(dto); // 알림

        return service.addReplyLike(dto);
    }

    //commentController 사용
//    @GetMapping("/ReplyLikeCount")
//    public int ReplyLikeCount(@RequestParam("re_id")int re_id){
//
//        return service.replyLikeCount(re_id);
//    }

}
