package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.BoardDao;
import com.teamc.moodtracker.dao.CommentDao;
import com.teamc.moodtracker.dao.NotificationDao;
import com.teamc.moodtracker.dto.*;
import com.teamc.moodtracker.dto.ReplyDto;
import com.teamc.moodtracker.dto.chat.SendChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private BoardDao boardDao;
    @Autowired
    private CommentDao jh_commentDao;

    private final SimpMessagingTemplate messagingTemplate;

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

    @Override
    public void addComment_SaveNotificationAndSendAlert(CommentDto commentDto) { // 댓글 작성
        int m_id_from = commentDto.getM_id();
        int b_id = commentDto.getB_id();
        BoardDetailDto bdto = boardDao.getBoardDetail(b_id);
        int m_id_to = bdto.getM_id();
        String cm_content = commentDto.getCm_content(); // 작성한 댓글 내용
        if (cm_content.length() > 20) {
            cm_content = cm_content.substring(0, 20) + "...";
        }
        if (m_id_from != m_id_to) { // 본인의 게시물에 상호작용한 내용은 알림을 보내지 않습니다.
            // Notice 테이블에 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("comment");
            notificationDto.setN_content(cm_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id=" + b_id);
            notificationDao.insertNotice(notificationDto);
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("comment")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(cm_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/" + m_id_to, alert);
        }
    }

    @Override
    public void addCommentLike_SaveNotificationAndSendAlert(CommentLikeDto commentLikeDto) { // 댓글 좋아요
        int m_id_from = commentLikeDto.getM_id();
        int cm_id = commentLikeDto.getCm_id(); // 대상 댓글 ID -> comments테이블에서 m_id 획득
        CommentDto cmdto = jh_commentDao.getCommentDetail(cm_id);
        int m_id_to = cmdto.getMember().getM_id();
        int b_id = cmdto.getB_id();
        String cm_content = cmdto.getCm_content(); // 대상 댓글 내용
        if (cm_content.length() > 20) {
            cm_content = cm_content.substring(0, 20) + "...";
        }
        if (m_id_from != m_id_to) { // 본인의 게시물에 상호작용한 내용은 알림을 보내지 않습니다.
            // DB 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("commentlike");
            notificationDto.setN_content(cm_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id=" + b_id);
            notificationDao.insertNotice(notificationDto);
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("commentlike")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(cm_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/" + m_id_to, alert);
        }
    }

    @Override
    public void addBoardLike_SaveNotificationAndSendAlert(BoardLikeDto boardLikeDto) { // 게시글 좋아요
        int m_id_from = boardLikeDto.getM_id(); // 알림 보낸 사람
        int b_id = boardLikeDto.getB_id();
        BoardDetailDto bdto = boardDao.getBoardDetail(b_id); // 알림 받을 사람
        int m_id_to = bdto.getM_id();
        String b_content = bdto.getB_content();
        if (b_content.length() > 20) {
            b_content = b_content.substring(0, 20) + "...";
        }
        if (m_id_from != m_id_to) { // 본인의 게시물에 상호작용한 내용은 알림을 보내지 않습니다.
            // DB 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("boardlike");
            notificationDto.setN_content(b_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id=" + b_id);
            notificationDao.insertNotice(notificationDto);
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("boardlike")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(b_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/" + m_id_to, alert);
        }
    }

    @Override
    public void addReply_SaveNotificationAndSendAlert(ReplyDto dto) { // 대댓글 작성
        int m_id_from = dto.getM_id(); // 알림 보낸 사람(대댓글 작성자)
        int cm_id = dto.getCm_id(); // -> 대상 댓글 ID
        CommentDto cdto = jh_commentDao.getCommentDetail(cm_id); //
        int m_id_to = cdto.getMember().getM_id(); // 알림 받을 사람
        int b_id = cdto.getB_id(); // 대댓글이 달린 게시물id(-> url)
        String re_content = dto.getRe_content(); // 작성한 대댓글 내용
        if (re_content.length() > 20) {
            re_content = re_content.substring(0, 20) + "...";
        }
        if (m_id_from != m_id_to) {
            // DB 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("reply");
            notificationDto.setN_content(re_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id=" + b_id);
            notificationDao.insertNotice(notificationDto);
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("reply")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(re_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/" + m_id_to, alert);
        }
    }

    @Override
    public void addReplyLike_SaveNotificationAndSendAlert(ReplyLikeDto replyLikeDto) { // 대댓글 좋아요
        int m_id_from = replyLikeDto.getM_id(); // 알림 보낸 사람
        int re_id = replyLikeDto.getRe_id(); // 대상 대댓글 id -> reply테이블에서 m_id, re_content조회
        ReplyDto rldto = notificationDao.getReplyDetail(re_id);
        int cm_id = rldto.getCm_id(); // 댓글 id
        CommentDto cdto = jh_commentDao.getCommentDetail(cm_id); // comments테이블에서 b_id 조회
        int b_id = cdto.getB_id();
        int m_id_to = rldto.getM_id();
        String re_content = rldto.getRe_content(); // 대상 대댓글 내용
        if (re_content.length() > 20) {
            re_content = re_content.substring(0, 20) + "...";
        }
        if (m_id_from != m_id_to) {
            // DB 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("replylike");
            notificationDto.setN_content(re_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id=" + b_id);
            notificationDao.insertNotice(notificationDto);
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("replylike")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(re_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/" + m_id_to, alert);
        }
    }

    @Override
    public void sendChat_SendAlert(SendChat sendRequest) {
        int m_id_from = sendRequest.getMemberId(); // 알림 보낸 사람( 채팅 보낸 사람 )
        int m_id_to = sendRequest.getOtherMemberId(); // 알림 받을 사람 ( 채팅 받을 사람 )
        String message = sendRequest.getMessage();
        if (message.length() > 20) {
            message = message.substring(0, 20) + "...";
        }
        if (m_id_from != m_id_to) {
            // DB 저장 X :: 채팅은 별도로 Notice테이블에 저장하지 않습니다.
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("chat")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(message).build();
            messagingTemplate.convertAndSend("/topic/notiChat/" + m_id_to, alert);
        }
    }

    // @Override
    // public void makeFollow_SaveNotificationAndSendAlert(FollowRequestDto
    // followRequestDTO) {
    // int m_id_from = followRequestDTO.getFollower_id(); // 팔로우 하는 사람
    // int m_id_to = followRequestDTO.getFollowed_id(); // 팔로우 당하는 사람
    // String fw_content = "";
    // if(m_id_from != m_id_to){ // 자기자신 팔로우 불가 -> FollowController에서 검사는 함
    // // DB 저장
    // NotificationDto notificationDto = new NotificationDto();
    // notificationDto.setM_id_to(m_id_to);
    // notificationDto.setM_id_from(m_id_from);
    // notificationDto.setN_type("follow");
    // notificationDto.setN_content(fw_content);
    // notificationDto.setN_url(""); // 팔로우 알림 클릭 시 이동할 페이지.. 팔로우한 사람 회원페이지?
    // notificationDao.insertNotice(notificationDto);
    // // 알림 전송
    // Alert alert = Alert.builder()
    // .type("follow")
    // .m_id_to(m_id_to)
    // .m_id_from(m_id_from)
    // .m_content(fw_content).build();
    // messagingTemplate.convertAndSend("/topic/notiChat/"+ m_id_to, alert);
    // }
    // }
}
