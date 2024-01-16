package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.NotificationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    public List<NotificationDto> selectMyNotificationAll(int m_id);

    public List<NotificationDto> selectMyNotificationFollow(int m_id);
    public List<NotificationDto> selectMyNotificationComment(int m_id);
    public List<NotificationDto> selectMyNotificationLike(int m_id);

    public int selectUnreadNumber(int m_id);

    public void readNotice(int m_id_to, int n_id);

    public void deleteNotice(int m_id_to, int n_id);
}
