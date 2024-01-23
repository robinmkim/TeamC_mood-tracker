package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.ReplyLikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyLikeDao {
    public boolean isMyLikeReply(ReplyLikeDto dto);
    public int getRelike_id(ReplyLikeDto dto);
    public List<Integer> getRelike_idAll (int re_id);
    public int addReplyLike(ReplyLikeDto dto);

    public void delReplyLike(int relike_id);

    public int replyLikeCount(int re_id);
}
