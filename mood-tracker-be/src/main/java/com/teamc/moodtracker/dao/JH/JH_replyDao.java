package com.teamc.moodtracker.dao.JH;

import com.teamc.moodtracker.dto.JH.JH_ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JH_replyDao {
    public int replyCount(int cm_id);

    public List<JH_ReplyDto> getReplyLis(int cm_id);

    public int addReply(JH_ReplyDto dto);

    public void delReply(int re_id);


    public List<Integer> getRe_idList(int cm_id);

    public JH_ReplyDto getReplyDetail(int re_id);






}
