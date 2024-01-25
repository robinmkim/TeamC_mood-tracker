package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.ReplyDto;
import com.teamc.moodtracker.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationDao {

    public List<NotificationDto> selectMyNotificationAll(int m_id_to);

    public List<NotificationDto> selectMyNotificationFollow(int m_id_to);

    public List<NotificationDto> selectMyNotificationComment(int m_id_to);

    public List<NotificationDto> selectMyNotificationLike(int m_id_to);

    public int selectUnreadNumber(int m_id_to);

    public void readNotice(int m_id_to, int n_id);

    public void deleteNotice(int m_id_to, int n_id);

    public void insertNotice(NotificationDto notificationDto);

    public ReplyDto getReplyDetail(int re_id);

    public void deleteNoticeAll(int mId);

    public void deleteNoticeAllFollow(int mId);

    public void deleteNoticeAllComment(int mId);

    public void deleteNoticeAllLike(int mId);
}
