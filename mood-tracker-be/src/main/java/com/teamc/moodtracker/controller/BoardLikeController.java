package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.BoardLikeDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.BoardLikeService;
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
@RequestMapping("/jh_postLike")
public class BoardLikeController {
    @Autowired
    private BoardLikeService service;
    @Autowired
    private NotificationService notificationService; //윤영호

    @GetMapping("/isMylike")
    public boolean isMyLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("b_id")int b_id){
        BoardLikeDto dto = new BoardLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setB_id(b_id);
        return service.isMyLike(dto);
    }

//    @GetMapping("/getBlike_id")
//    public int getBlike_id(@ModelAttribute Board_LikeDto dto){
//        System.out.println("???");
//
//        return service.getBlike_id(dto);
//    }

    @GetMapping("/delBoardLike")
    public void delBoardLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("b_id")int b_id){
        BoardLikeDto dto = new BoardLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setB_id(b_id);
        service.delBoardLike(service.getBlike_id(dto));
    }

    @GetMapping("/addBoardLike")
    public int addBoardLike(@AuthenticationPrincipal MemberDto memberDto,@RequestParam("b_id")int b_id) {
        System.out.println("addBoardLike");
        BoardLikeDto dto = new BoardLikeDto();
        dto.setM_id(memberDto.getM_id());
        dto.setB_id(b_id);
        System.out.println("b_id: "+dto.getB_id());

        notificationService.addBoardLike_SaveNotificationAndSendAlert(dto); // 알림 // 윤영호

        return service.addBoardLike(dto);
    }

}
