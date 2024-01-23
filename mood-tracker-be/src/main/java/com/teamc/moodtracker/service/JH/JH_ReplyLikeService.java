package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dto.Comment_LikeDto;
import com.teamc.moodtracker.dto.Reply_LikeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JH_ReplyLikeService {

    public boolean isMyLikeReply(Reply_LikeDto dto);
    public int getRelike_id(Reply_LikeDto dto);

    public List<Integer> getRelike_idAll(int re_id);


    public int addReplyLike(Reply_LikeDto dto);

    public void delReplyLike(int relike_id);
    public void delReplyLikeAll(int re_id);

    public int replyLikeCount(int re_id);
}
