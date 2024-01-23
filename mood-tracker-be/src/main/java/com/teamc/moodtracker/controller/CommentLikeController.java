package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.CommentLikeDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.CommentLikeService;
import com.teamc.moodtracker.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/jh_CommentLike")
public class CommentLikeController {
    @Autowired
    private CommentLikeService service;

    @Autowired
    private NotificationService notificationService;

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

    @GetMapping("/delCommentLike")
    public void delCommentLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("cm_id")int cm_id){
        System.out.println("delCommentLike");
        System.out.println("cm_id: "+ cm_id);
        CommentLikeDto dto = new CommentLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setCm_id(cm_id);
        int cmlike_id = service.getCmlike_id(dto);
        System.out.println("cmlike_id: "+cmlike_id);
        service.delCommentLike(cmlike_id);
    }

    @GetMapping("/addCommentLike")
    public int addCommentLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("cm_id")int cm_id) {
        System.out.println("addCommentLike");
        CommentLikeDto dto = new CommentLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setCm_id(cm_id);
        System.out.println("cm_id: "+dto.getCm_id());

        notificationService.addCommentLike_SaveNotificationAndSendAlert(dto); // 알림 윤영호

        return service.addCommentLike(dto);
    }

    @GetMapping("/commentLikeCount")
    public int commentLikeCount(@RequestParam("cm_id") int cm_id){

        return service.commentLikeCount(cm_id);
    }

}
