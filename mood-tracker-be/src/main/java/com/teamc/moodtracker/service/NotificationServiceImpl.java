package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.MemberDao;
import com.teamc.moodtracker.dao.NotificationDao;
import com.teamc.moodtracker.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationDao notificationDao;


    @Override
    public List<NotificationDto> selectMyNotificationAll(int m_id_to) {
        return notificationDao.selectMyNotificationAll(m_id_to);
    }

    @Override
    public List<NotificationDto> selectMyNotificationFollow(int m_id_to) {
        return notificationDao.selectMyNotificationFollow(m_id_to);
    }

    @Override
    public List<NotificationDto> selectMyNotificationComment(int m_id_to) {
        return notificationDao.selectMyNotificationComment(m_id_to);
    }

    @Override
    public List<NotificationDto> selectMyNotificationLike(int m_id_to) {
        return notificationDao.selectMyNotificationLike(m_id_to);
    }

    @Override
    public int selectUnreadNumber(int m_id_to) {
        return notificationDao.selectUnreadNumber(m_id_to);
    }

    @Override
    public void readNotice(int m_id_to, int n_id) {
        notificationDao.readNotice(m_id_to, n_id);
    }

    @Transactional
    @Override
    public void deleteNotice(int m_id_to, int n_id) {
        notificationDao.deleteNotice(m_id_to, n_id);
    }


}
