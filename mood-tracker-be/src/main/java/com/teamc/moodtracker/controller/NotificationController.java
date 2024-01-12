package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.NotificationDto;
import com.teamc.moodtracker.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping("/select/all")
    public List<NotificationDto> selectMyNotificationAll(int n_user){
        return  notificationService.selectMyNotificationAll(n_user);
    }
    @GetMapping("/select/follow")
    public List<NotificationDto> selectMyNotificationFollow(int n_user){
        return  notificationService.selectMyNotificationFollow(n_user);
    }
    @GetMapping("/select/comment")
    public List<NotificationDto> selectMyNotificationComment(int n_user){
        return  notificationService.selectMyNotificationComment(n_user);
    }
    @GetMapping("/select/like")
    public List<NotificationDto> selectMyNotificationLike(int n_user){
        return  notificationService.selectMyNotificationLike(n_user);
    }

}
