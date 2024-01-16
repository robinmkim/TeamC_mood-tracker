package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.JH_CommentLikeDao;
import com.teamc.moodtracker.dao.JH_ReplyLikeDao;
import com.teamc.moodtracker.dto.Comment_LikeDto;
import com.teamc.moodtracker.dto.Reply_LikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JH_ReplyLikeServiceImpl implements JH_ReplyLikeService{
    @Autowired
    private JH_ReplyLikeDao dao;


    @Override
    public boolean isMyLikeReply(Reply_LikeDto dto) {
        return dao.isMyLikeReply(dto);
    }

    @Override
    public int getRelike_id(Reply_LikeDto dto) {
        return dao.getRelike_id(dto);
    }

    @Override
    public int addReplyLike(Reply_LikeDto dto) {
        return dao.addReplyLike(dto);
    }

    @Override
    public void delReplyLike(int relike_id) {
        dao.delReplyLike(relike_id);
    }
}
