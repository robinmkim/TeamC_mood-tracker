package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.CommentLikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentLikeDao {
    public boolean isMyLikeComment(CommentLikeDto dto);
    public int getCmlike_id(CommentLikeDto dto);
    public List<Integer> getCmlike_idAll(int cm_id);

    public int addCommentLike(CommentLikeDto dto);

    public void delCommentLike(int cmlike_id);

    public int commentLikeCount(int cm_id);


}
