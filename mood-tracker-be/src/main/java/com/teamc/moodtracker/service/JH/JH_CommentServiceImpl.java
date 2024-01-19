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
    @Autowired
    private JH_CommentLikeService likeService;


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
        if(likeService.commentLikeCount(cm_id) > 0) {
            likeService.delCommentLikeAll(cm_id);
        }
        commentDao.delComment(cm_id);
    }

    @Override
    public List<Integer> getCm_idList(int b_id) {
        return commentDao.getCm_idList(b_id);
    }

    @Override
    public JH_CommentDto getCommentDetail(int cm_id) {
        return commentDao.getCommentDetail(cm_id);
    }


}
