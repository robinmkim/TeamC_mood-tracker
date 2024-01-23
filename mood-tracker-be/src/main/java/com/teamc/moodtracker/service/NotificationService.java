package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.*;
import com.teamc.moodtracker.dto.ReplyDto;
import com.teamc.moodtracker.dto.chat.SendChat;
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
    public void addComment_SaveNotificationAndSendAlert(CommentDto commentDto);

    public void addCommentLike_SaveNotificationAndSendAlert(CommentLikeDto commentLikeDto);

    public void addBoardLike_SaveNotificationAndSendAlert(BoardLikeDto boardLikeDto);

    public void addReply_SaveNotificationAndSendAlert(ReplyDto dto);

    public void addReplyLike_SaveNotificationAndSendAlert(ReplyLikeDto replyLikeDto);

    public void sendChat_SendAlert(SendChat sendRequest);

    <<<<<<<HEAD
    // public void makeFollow_SaveNotificationAndSendAlert(FollowRequestDto
    // followRequestDTO); // 팔로우 부분 pull 한 다음 맞게 수정할 것
    =======
    // public void makeFollow_SaveNotificationAndSendAlert(FollowRequestDto
    // followRequestDTO); // 팔로우 부분 pull 한 다음 맞게 수정할 것
    >>>>>>>08b1e34 (🚚 boot 프로젝트 디렉토리 수정 및 파일 이름 재지정)
}
