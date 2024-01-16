package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.JH_replyDao;
import com.teamc.moodtracker.dao.ReportDao;
import com.teamc.moodtracker.dto.JH_ReplyDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JH_ReplyServiceImpl implements JH_ReplyService{
    @Autowired
    private JH_replyDao replyDao;


    @Override
    public List<JH_ReplyDto> getReplyLis(int cm_id) {
        return replyDao.getReplyLis(cm_id);
    }

    @Override
    public int addReply(JH_ReplyDto dto) {
        return replyDao.addReply(dto);
    }

    @Override
    public void delReply(int re_id) {

        replyDao.delReply(re_id);
    }


}
