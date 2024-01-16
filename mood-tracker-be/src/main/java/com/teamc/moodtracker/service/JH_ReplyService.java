package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.JH_ReplyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JH_ReplyService {
    public List<JH_ReplyDto> getReplyLis(int cm_id);

    public int addReply(JH_ReplyDto dto);

    public void delReply(int re_id);

}
