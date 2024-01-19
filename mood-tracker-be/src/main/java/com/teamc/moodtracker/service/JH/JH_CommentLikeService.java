package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dto.Board_LikeDto;
import com.teamc.moodtracker.dto.Comment_LikeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JH_CommentLikeService {

    public boolean isMyLikeComment(Comment_LikeDto dto);
    public int getCmlike_id(Comment_LikeDto dto);

    public int addCommentLike(Comment_LikeDto dto);

    public void delCommentLike(int cmlike_id);
    public void delCommentLikeAll(int cm_id);
//    public void delReplyLikeAll(int cm_id);


    public int commentLikeCount(int cm_id);
}
