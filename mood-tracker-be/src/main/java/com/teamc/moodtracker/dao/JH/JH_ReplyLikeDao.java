package com.teamc.moodtracker.dao.JH;

import com.teamc.moodtracker.dto.Board_LikeDto;
import com.teamc.moodtracker.dto.Reply_LikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JH_ReplyLikeDao {
    public boolean isMyLikeReply(Reply_LikeDto dto);
    public int getRelike_id(Reply_LikeDto dto);
    public List<Integer> getRelike_idAll (int re_id);
    public int addReplyLike(Reply_LikeDto dto);

    public void delReplyLike(int relike_id);

    public int replyLikeCount(int re_id);
}
