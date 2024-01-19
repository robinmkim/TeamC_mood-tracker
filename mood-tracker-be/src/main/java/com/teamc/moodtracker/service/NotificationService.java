package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.Board_LikeDto;
import com.teamc.moodtracker.dto.CommentDto;
import com.teamc.moodtracker.dto.Comment_LikeDto;
import com.teamc.moodtracker.dto.JH.JH_CommentDto;
import com.teamc.moodtracker.dto.JH.JH_ReplyDto;
import com.teamc.moodtracker.dto.NotificationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    public List<NotificationDto> selectMyNotificationAll(int m_id);

    public List<NotificationDto> selectMyNotificationFollow(int m_id);
    public List<NotificationDto> selectMyNotificationComment(int m_id);
    public List<NotificationDto> selectMyNotificationLike(int m_id);

    public int selectUnreadNumber(int m_id_to);

    public void readNotice(int m_id_to, int n_id);

    public void deleteNotice(int m_id_to, int n_id);

    // comment 저장 -> Notification객체 생성 -> 저장 -> 알림 전송
    public void addComment_SaveNotificationAndSendAlert(JH_CommentDto commentDto);

    public void addCommentLike_SaveNotificationAndSendAlert(Comment_LikeDto commentLikeDto);

    public void addBoardLike_SaveNotificationAndSendAlert(Board_LikeDto boardLikeDto);

    public void addReply_SaveNotificationAndSendAlert(JH_ReplyDto dto);
}
