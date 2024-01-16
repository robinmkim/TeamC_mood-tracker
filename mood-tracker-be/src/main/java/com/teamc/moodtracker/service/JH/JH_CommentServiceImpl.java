package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dao.JH.JH_CommentDao;
import com.teamc.moodtracker.dao.JH.JH_replyDao;
import com.teamc.moodtracker.dto.JH.JH_CommentDto;
import com.teamc.moodtracker.dto.JH.JH_ReplyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class JH_CommentServiceImpl implements JH_CommentService{

    @Autowired
    private JH_CommentDao commentDao;
    @Autowired
    private JH_replyDao replyDao;


    @Override
    public int commentCount(int b_id) {
        return commentDao.commentCount(b_id);
    }

    @Override
    public int replyCount(int cm_id) {
        return replyDao.replyCount(cm_id);
    }

    @Override
    public List<JH_CommentDto> getCommentList(int b_id) {
        return commentDao.getCommentList(b_id);
    }

    @Override
    public List<JH_CommentDto> getCommentListDetail(int b_id) {
        return commentDao.getCommentListDetail(b_id);
    }

    @Override
    public int addComment(JH_CommentDto dto) {
        return commentDao.addComment(dto);
    }

    @Override
    @Transactional
    public void delComment(int cm_id) {
        System.out.println("delComment : " + cm_id);

        // 리플이 있는지 검색 후 있는만큼 리플지우기.
        List<JH_ReplyDto> delReplyList = replyDao.getReplyLis(cm_id);
        if (replyDao.replyCount(cm_id) == 0) {

            commentDao.delComment(cm_id);
            return;
        }
        System.out.println(delReplyList);
        for (JH_ReplyDto r : delReplyList) {
            System.out.println("re_id: "+r.getRe_id());
            replyDao.delReply(r.getRe_id());
        }
        commentDao.delComment(cm_id);
    }


}
