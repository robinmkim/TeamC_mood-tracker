package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.MemberDao;
import com.teamc.moodtracker.dao.NotificationDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService{
    private NotificationDao notificationDao;


}
