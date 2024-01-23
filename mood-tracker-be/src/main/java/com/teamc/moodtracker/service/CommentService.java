package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.CommentDao;
import com.teamc.moodtracker.dao.ReplyDao;
import com.teamc.moodtracker.dto.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ReplyDao replyDao;
    @Autowired
    private CommentLikeService likeService;


    public int commentCount(int b_id) {
        return commentDao.commentCount(b_id);
    }

    public int replyCount(int cm_id) {
        return replyDao.replyCount(cm_id);
    }

    public List<CommentDto> getCommentList(int b_id) {

        return commentDao.getCommentList(b_id);
    }

    public List<CommentDto> getCommentListDetail(int b_id) {
        return commentDao.getCommentListDetail(b_id);
    }

    public int addComment(CommentDto dto) {
        return commentDao.addComment(dto);
    }

    @Transactional
    public void delComment(int cm_id) {
        System.out.println("delComment : " + cm_id);
        if(likeService.commentLikeCount(cm_id) > 0) {
            likeService.delCommentLikeAll(cm_id);
        }
        commentDao.delComment(cm_id);
    }

    public List<Integer> getCm_idList(int b_id) {
        return commentDao.getCm_idList(b_id);
    }

    public CommentDto getCommentDetail(int cm_id) {
        return commentDao.getCommentDetail(cm_id);
    }


}
