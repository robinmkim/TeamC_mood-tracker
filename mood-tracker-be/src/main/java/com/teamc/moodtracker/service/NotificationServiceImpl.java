package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.MemberDao;
import com.teamc.moodtracker.dao.NotificationDao;
import com.teamc.moodtracker.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationDao notificationDao;


    @Override
    public List<NotificationDto> selectMyNotificationAll(int n_user) {
        return notificationDao.selectMyNotificationAll(n_user);
    }

    @Override
    public List<NotificationDto> selectMyNotificationFollow(int n_user) {
        return notificationDao.selectMyNotificationFollow(n_user);
    }

    @Override
    public List<NotificationDto> selectMyNotificationComment(int n_user) {
        return notificationDao.selectMyNotificationComment(n_user);
    }

    @Override
    public List<NotificationDto> selectMyNotificationLike(int n_user) {
        return notificationDao.selectMyNotificationLike(n_user);
    }


}
