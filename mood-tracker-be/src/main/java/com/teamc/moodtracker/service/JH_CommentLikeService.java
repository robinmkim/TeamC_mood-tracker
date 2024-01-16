package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dto.Board_LikeDto;
import com.teamc.moodtracker.dto.Comment_LikeDto;
import org.springframework.stereotype.Service;

@Service
public interface JH_CommentLikeService {

    public boolean isMyLikeComment(Comment_LikeDto dto);
    public int getCmlike_id(Comment_LikeDto dto);

    public int addCommentLike(Comment_LikeDto dto);

    public void delCommentLike(int cmlike_id);
}
