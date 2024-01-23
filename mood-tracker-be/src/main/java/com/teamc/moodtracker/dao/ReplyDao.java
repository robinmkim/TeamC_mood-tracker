package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.ReplyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDao {
    public int replyCount(int cm_id);

    public List<ReplyDto> getReplyLis(int cm_id);

    public int addReply(ReplyDto dto);

    public void delReply(int re_id);


    public List<Integer> getRe_idList(int cm_id);

    public ReplyDto getReplyDetail(int re_id);






}
