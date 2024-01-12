package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.NotificationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    public List<NotificationDto> selectMyNotificationAll(int n_user);

    public List<NotificationDto> selectMyNotificationFollow(int n_user);
    public List<NotificationDto> selectMyNotificationComment(int n_user);
    public List<NotificationDto> selectMyNotificationLike(int n_user);
}
