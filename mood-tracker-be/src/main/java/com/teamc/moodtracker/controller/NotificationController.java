package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.Alert;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.NotificationDto;
import com.teamc.moodtracker.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PatchExchange;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.System.in;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private final NotificationService notificationService;

    private final String imageDirectory = "src/main/resources/static/images/";


    private final SimpMessagingTemplate messagingTemplate;


    @GetMapping("/select/all") // DB에 저장된 알림들을 불러옴
    public List<NotificationDto>  selectMyNotificationAll(@AuthenticationPrincipal MemberDto memberDto) throws IOException {
        List<NotificationDto> noticeList =notificationService.selectMyNotificationAll(memberDto.getM_id());
        for(NotificationDto bean : noticeList){
            String encodedString = new String();
            String imagePath = imageDirectory + bean.getMemberDto().getM_img_name();
            File file = new File(imagePath); // 파일 열기
            if(file.exists()){ // 파일이 존재하는지 검사
                System.out.println("File exists.");
                byte[] fileContent = FileCopyUtils.copyToByteArray(file); // file을 byte로 변경
                encodedString = Base64.getEncoder().encodeToString(fileContent); // byte를 base64로  인코딩
                encodedString = "data:image/jpeg;base64, " + encodedString;
                bean.getMemberDto().setImg_byte(encodedString);
            }else{
                System.out.println("File Not exists");
            }
        }
        return  noticeList;
    }

    @GetMapping("/select/follow")
    public List<NotificationDto>  selectMyNotificationFollow(@AuthenticationPrincipal MemberDto memberDto) throws IOException {
        List<NotificationDto> noticeList =notificationService.selectMyNotificationFollow(memberDto.getM_id());
        for(NotificationDto bean : noticeList){
            String encodedString = new String();
            String imagePath = imageDirectory + bean.getMemberDto().getM_img_name();
            File file = new File(imagePath); // 파일 열기
            if(file.exists()){ // 파일이 존재하는지 검사
                System.out.println("File exists.");
                byte[] fileContent = FileCopyUtils.copyToByteArray(file); // file을 byte로 변경
                encodedString = Base64.getEncoder().encodeToString(fileContent); // byte를 base64로  인코딩
                encodedString = "data:image/jpeg;base64, " + encodedString;
                bean.getMemberDto().setImg_byte(encodedString);
            }else{
                System.out.println("File Not exists");
            }
        }
        return  noticeList;
    }

    @GetMapping("/select/comment")
    public List<NotificationDto>  selectMyNotificationComment(@AuthenticationPrincipal MemberDto memberDto) throws IOException {
        List<NotificationDto> noticeList =notificationService.selectMyNotificationComment(memberDto.getM_id());
        for(NotificationDto bean : noticeList){
            String encodedString = new String();
            String imagePath = imageDirectory + bean.getMemberDto().getM_img_name();
            File file = new File(imagePath); // 파일 열기
            if(file.exists()){ // 파일이 존재하는지 검사
                System.out.println("File exists.");
                byte[] fileContent = FileCopyUtils.copyToByteArray(file); // file을 byte로 변경
                encodedString = Base64.getEncoder().encodeToString(fileContent); // byte를 base64로  인코딩
                encodedString = "data:image/jpeg;base64, " + encodedString;
                bean.getMemberDto().setImg_byte(encodedString);
            }else{
                System.out.println("File Not exists");
            }
        }
        return  noticeList;
    }

    @GetMapping("/select/like")
    public List<NotificationDto>  selectMyNotificationLike(@AuthenticationPrincipal MemberDto memberDto) throws IOException {
        List<NotificationDto> noticeList =notificationService.selectMyNotificationLike(memberDto.getM_id());
        for(NotificationDto bean : noticeList){
            String encodedString = new String();
            String imagePath = imageDirectory + bean.getMemberDto().getM_img_name();
            File file = new File(imagePath); // 파일 열기
            if(file.exists()){ // 파일이 존재하는지 검사
                System.out.println("File exists.");
                byte[] fileContent = FileCopyUtils.copyToByteArray(file); // file을 byte로 변경
                encodedString = Base64.getEncoder().encodeToString(fileContent); // byte를 base64로  인코딩
                encodedString = "data:image/jpeg;base64, " + encodedString;
                bean.getMemberDto().setImg_byte(encodedString);
            }else{
                System.out.println("File Not exists");
            }
        }
        return  noticeList;
    }

    @GetMapping("/select/unread")
    public int selectUnreadNumber(@AuthenticationPrincipal MemberDto memberDto){
        return notificationService.selectUnreadNumber(memberDto.getM_id());
    }

    @PatchMapping("/read")
    public int readNotice(@AuthenticationPrincipal MemberDto memberDto,@RequestBody Map<String, Integer> requestMap){
        Integer n_id = requestMap.get("n_id");
        try{
            notificationService.readNotice(memberDto.getM_id(), n_id);
            return 1;
        }catch(Exception e) {
            return 0;
        }
    }

    @DeleteMapping("/delete")
    public int deleteNotice(@AuthenticationPrincipal MemberDto memberDto,@RequestBody Map<String, Integer> requestMap){
        Integer n_id = requestMap.get("n_id");
        System.out.println("n_id = "+ n_id);
        try{
            notificationService.deleteNotice(memberDto.getM_id(), n_id);
            return 1;
        }catch(Exception e) {
            System.out.println(e);
            return 0;
        }
    }

}
