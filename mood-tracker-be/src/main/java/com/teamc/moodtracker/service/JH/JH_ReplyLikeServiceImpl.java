package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dao.JH.JH_ReplyLikeDao;
import com.teamc.moodtracker.dto.Reply_LikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Integer> getRelike_idAll(int re_id) {
        return dao.getRelike_idAll(re_id);
    }

    @Override
    public int addReplyLike(Reply_LikeDto dto) {
        return dao.addReplyLike(dto);
    }

    @Override
    public void delReplyLike(int relike_id) {
        dao.delReplyLike(relike_id);
    }

    @Transactional
    @Override
    public void delReplyLikeAll(int re_id) {
        List<Integer> likeList = dao.getRelike_idAll(re_id);
        for (int like : likeList){
            dao.delReplyLike(like);
        }
    }

    @Override
    public int replyLikeCount(int re_id) {
        return dao.replyLikeCount(re_id);
    }
}
