package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.JH_BoardLikeDao;
import com.teamc.moodtracker.dao.JH_CommentLikeDao;
import com.teamc.moodtracker.dto.Board_LikeDto;
import com.teamc.moodtracker.dto.Comment_LikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JH_CommentLikeServiceImpl implements JH_CommentLikeService{
    @Autowired
    private JH_CommentLikeDao dao;

    @Override
    public boolean isMyLikeComment(Comment_LikeDto dto) {
        return false;
    }

    @Override
    public int getCmlike_id(Comment_LikeDto dto) {
        return 0;
    }

    @Override
    public int addCommentLike(Comment_LikeDto dto) {
        return 0;
    }

    @Override
    public void delCommentLike(int cmlike_id) {

    }
}
