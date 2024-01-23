package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.ReplyLikeDao;
import com.teamc.moodtracker.dto.ReplyLikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ReplyLikeService {
    @Autowired
    private ReplyLikeDao dao;


    public boolean isMyLikeReply(ReplyLikeDto dto) {
        return dao.isMyLikeReply(dto);
    }

    public int getRelike_id(ReplyLikeDto dto) {
        return dao.getRelike_id(dto);
    }

    public List<Integer> getRelike_idAll(int re_id) {
        return dao.getRelike_idAll(re_id);
    }

    public int addReplyLike(ReplyLikeDto dto) {
        return dao.addReplyLike(dto);
    }

    public void delReplyLike(int relike_id) {
        dao.delReplyLike(relike_id);
    }

    @Transactional
    public void delReplyLikeAll(int re_id) {
        List<Integer> likeList = dao.getRelike_idAll(re_id);
        for (int like : likeList){
            dao.delReplyLike(like);
        }
    }

    public int replyLikeCount(int re_id) {
        return dao.replyLikeCount(re_id);
    }
}
