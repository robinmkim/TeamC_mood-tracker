package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.CommentLikeDao;
import com.teamc.moodtracker.dto.CommentLikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CommentLikeService{
    @Autowired
    private CommentLikeDao dao;

    public boolean isMyLikeComment(CommentLikeDto dto) {
        return dao.isMyLikeComment(dto);
    }

    public int getCmlike_id(CommentLikeDto dto) {
        return dao.getCmlike_id(dto);
    }


    public int addCommentLike(CommentLikeDto dto) {
        return dao.addCommentLike(dto);
    }

    public void delCommentLike(int cmlike_id) {
        dao.delCommentLike(cmlike_id);
    }

    @Transactional
    public void delCommentLikeAll(int cm_id) {
        List<Integer> likeList = dao.getCmlike_idAll(cm_id);
        for (int like : likeList) {
            dao.delCommentLike(like);
        }
    }

    public int commentLikeCount(int cm_id) {
        return dao.commentLikeCount(cm_id);
    }
}
