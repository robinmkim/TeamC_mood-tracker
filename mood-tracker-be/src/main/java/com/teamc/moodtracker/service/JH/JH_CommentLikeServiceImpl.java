package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dao.JH.JH_CommentLikeDao;
import com.teamc.moodtracker.dto.Comment_LikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class JH_CommentLikeServiceImpl implements JH_CommentLikeService{
    @Autowired
    private JH_CommentLikeDao dao;

    @Override
    public boolean isMyLikeComment(Comment_LikeDto dto) {
        return dao.isMyLikeComment(dto);
    }

    @Override
    public int getCmlike_id(Comment_LikeDto dto) {
        return dao.getCmlike_id(dto);
    }


    @Override
    public int addCommentLike(Comment_LikeDto dto) {
        return dao.addCommentLike(dto);
    }

    @Override
    public void delCommentLike(int cmlike_id) {
        dao.delCommentLike(cmlike_id);
    }

    @Transactional
    @Override
    public void delCommentLikeAll(int cm_id) {
        List<Integer> likeList = dao.getCmlike_idAll(cm_id);
        for (int like : likeList) {
            dao.delCommentLike(like);
        }
    }



    @Override
    public int commentLikeCount(int cm_id) {
        return dao.commentLikeCount(cm_id);
    }
}
