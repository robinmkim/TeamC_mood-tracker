package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationDao {


    public List<NotificationDto> selectMyNotificationAll(int  n_user);
    public List<NotificationDto> selectMyNotificationFollow(int n_user);

    public List<NotificationDto> selectMyNotificationComment(int n_user);

    public List<NotificationDto> selectMyNotificationLike(int n_user);

    public int selectUnreadNumber(int n_user);

}
