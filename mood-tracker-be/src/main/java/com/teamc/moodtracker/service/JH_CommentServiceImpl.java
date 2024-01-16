package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.CommentDao;
import com.teamc.moodtracker.dao.JH_CommentDao;
import com.teamc.moodtracker.dao.JH_replyDao;
import com.teamc.moodtracker.dto.JH_CommentDto;
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


}
