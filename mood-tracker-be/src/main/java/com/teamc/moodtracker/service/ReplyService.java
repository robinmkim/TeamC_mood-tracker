package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.ReplyDao;
import com.teamc.moodtracker.dto.ReplyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ReplyService{
    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private ReplyLikeService likeService;


    public List<ReplyDto> getReplyLis(int cm_id) {
        return replyDao.getReplyLis(cm_id);
    }

    public int addReply(ReplyDto dto) {
        return replyDao.addReply(dto);
    }

    @Transactional
    public void delReply(int re_id) {
        if (likeService.replyLikeCount(re_id) > 0) {
            likeService.delReplyLikeAll(re_id);
        }
        replyDao.delReply(re_id);
    }

    public int replyCount(int cm_id) {
        return replyDao.replyCount(cm_id);
    }

    public List<Integer> getRe_idList(int cm_id) {
        return replyDao.getRe_idList(cm_id);
    }

    public ReplyDto getReplyDetail(int re_id) {
        return replyDao.getReplyDetail(re_id);
    }


}
