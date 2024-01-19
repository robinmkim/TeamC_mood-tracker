package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dao.JH.JH_replyDao;
import com.teamc.moodtracker.dto.JH.JH_ReplyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class JH_ReplyServiceImpl implements JH_ReplyService{
    @Autowired
    private JH_replyDao replyDao;

    @Autowired
    private JH_ReplyLikeService likeService;


    @Override
    public List<JH_ReplyDto> getReplyLis(int cm_id) {
        return replyDao.getReplyLis(cm_id);
    }

    @Override
    public int addReply(JH_ReplyDto dto) {
        return replyDao.addReply(dto);
    }

    @Transactional
    @Override
    public void delReply(int re_id) {
        if (likeService.replyLikeCount(re_id) > 0) {
            likeService.delReplyLikeAll(re_id);
        }
        replyDao.delReply(re_id);
    }

    @Override
    public int replyCount(int cm_id) {
        return replyDao.replyCount(cm_id);
    }

    @Override
    public List<Integer> getRe_idList(int cm_id) {
        return replyDao.getRe_idList(cm_id);
    }

    @Override
    public JH_ReplyDto getReplyDetail(int re_id) {
        return replyDao.getReplyDetail(re_id);
    }


}
