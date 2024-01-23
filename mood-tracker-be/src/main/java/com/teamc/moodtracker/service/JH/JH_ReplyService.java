package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dao.JH.JH_replyDao;
import com.teamc.moodtracker.dto.JH.JH_ReplyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JH_ReplyService {
    public List<JH_ReplyDto> getReplyLis(int cm_id);

    public int addReply(JH_ReplyDto dto);

    public void delReply(int re_id);

    public int replyCount(int cm_id);

    public List<Integer> getRe_idList(int cm_id);
    public JH_ReplyDto getReplyDetail(int re_id);

}
