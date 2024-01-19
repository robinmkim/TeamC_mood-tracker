package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.BoardDao;
import com.teamc.moodtracker.dao.JH.JH_CommentDao;
import com.teamc.moodtracker.dao.NotificationDao;
import com.teamc.moodtracker.dto.*;
import com.teamc.moodtracker.dto.JH.JH_CommentDto;
import com.teamc.moodtracker.dto.JH.JH_ReplyDto;
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
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private BoardDao boardDao;
    @Autowired
    private JH_CommentDao jh_commentDao;

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
    public void addComment_SaveNotificationAndSendAlert(JH_CommentDto commentDto) { // 댓글 작성
        int m_id_from = commentDto.getM_id();
        int b_id = commentDto.getB_id();
        int m_id_to = boardDao.getMemberIdByBoardId(b_id);
        String cm_content = commentDto.getCm_content();

        if(m_id_from != m_id_to){ // 본인의 게시물에 상호작용한 내용은 알림을 보내지 않습니다.
            // Notice 테이블에 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("comment");
            notificationDto.setN_content(cm_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id="+b_id);
            notificationDao.insertNotice(notificationDto);
            //알림 전송
            Alert alert = Alert.builder()
                    .type("comment")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(cm_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/"+ m_id_to, alert);
        }
    }

    @Override
    public void addCommentLike_SaveNotificationAndSendAlert(Comment_LikeDto commentLikeDto) { // 댓글 좋아요
        int m_id_from = commentLikeDto.getM_id();
        int cm_id = commentLikeDto.getCm_id(); // 대상 댓글 ID -> comments테이블에서 m_id 획득

        JH_CommentDto cmdto = jh_commentDao.getCommentDetail(cm_id);
        int m_id_to = cmdto.getMember().getM_id();
        int b_id = cmdto.getB_id();
        String cm_content = cmdto.getCm_content();

        if(m_id_from != m_id_to){ // 본인의 게시물에 상호작용한 내용은 알림을 보내지 않습니다.
            //DB 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("commentlike");
            notificationDto.setN_content(cm_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id="+b_id);
            notificationDao.insertNotice(notificationDto);
            //알림 전송
            Alert alert = Alert.builder()
                    .type("commentlike")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(cm_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/"+ m_id_to, alert);
        }
    }

    @Override
    public void addBoardLike_SaveNotificationAndSendAlert(Board_LikeDto boardLikeDto) { // 게시글 좋아요
        int m_id_from = boardLikeDto.getM_id();  //알림 보낸 사람
        int b_id = boardLikeDto.getB_id();
        BoardDto bdto = boardDao.getBoardDetail(b_id); // 알림 받을 사람
        int m_id_to = bdto.getM_id();
        String b_content = bdto.getB_content();
        if(b_content.length() > 10){
            b_content = b_content.substring(0,10) + "...";
        }
        if(m_id_from != m_id_to){ // 본인의 게시물에 상호작용한 내용은 알림을 보내지 않습니다.
            // DB 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("boardlike");
            notificationDto.setN_content(b_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id="+b_id);
            notificationDao.insertNotice(notificationDto);
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("boardlike")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(b_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/"+ m_id_to, alert);
        }
    }

    @Override
    public void addReply_SaveNotificationAndSendAlert(JH_ReplyDto dto) { // 대댓글 작성
        int m_id_from = dto.getM_id(); // 알림 보낸 사람(대댓글 작성자)
        int cm_id = dto.getCm_id(); // -> 대상 댓글 ID
        JH_CommentDto cdto = jh_commentDao.getCommentDetail(cm_id); //
        int m_id_to = cdto.getMember().getM_id(); // 알림 받을 사람
        int b_id = cdto.getB_id();
        String re_content = dto.getRe_content(); // 대댓글 내용
        System.out.println("대댓글 작성 -> 알림 추가하기 전 검사");
        System.out.println("m_id_to = "+ m_id_to);
        System.out.println("m_id_from = " + m_id_from);
        System.out.println("검사 끝");
        if(m_id_from != m_id_to){
            // DB 저장
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setM_id_to(m_id_to);
            notificationDto.setM_id_from(m_id_from);
            notificationDto.setN_type("reply");
            notificationDto.setN_content(re_content);
            notificationDto.setN_url("http://localhost:8081/postDetail/?b_id="+b_id);
            notificationDao.insertNotice(notificationDto);
            // 알림 전송
            Alert alert = Alert.builder()
                    .type("reply")
                    .m_id_to(m_id_to)
                    .m_id_from(m_id_from)
                    .m_content(re_content).build();
            messagingTemplate.convertAndSend("/topic/notiChat/"+ m_id_to, alert);
        }
    }
}
