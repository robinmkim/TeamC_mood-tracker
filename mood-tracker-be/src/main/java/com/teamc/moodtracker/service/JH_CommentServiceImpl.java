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

    @Transactional
    @Override
    public int commentCount(int cm_bid) {
        return commentDao.commentCount(cm_bid);
    }

    @Override
    public int replyCount(int re_cmid) {
        return replyDao.replyCount(re_cmid);
    }

    @Override
    public List<JH_CommentDto> getCommentList(int cm_bid) {
        return commentDao.getCommentList(cm_bid);
    }

    @Override
    public List<JH_CommentDto> getCommentListDetail(int cm_bid) {
        return commentDao.getCommentListDetail(cm_bid);
    }
}
