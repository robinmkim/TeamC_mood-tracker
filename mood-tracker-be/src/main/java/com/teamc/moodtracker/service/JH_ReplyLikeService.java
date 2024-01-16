package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.Comment_LikeDto;
import com.teamc.moodtracker.dto.Reply_LikeDto;
import org.springframework.stereotype.Service;

@Service
public interface JH_ReplyLikeService {

    public boolean isMyLikeReply(Reply_LikeDto dto);
    public int getRelike_id(Reply_LikeDto dto);

    public int addReplyLike(Reply_LikeDto dto);

    public void delReplyLike(int relike_id);
}
