package com.teamc.moodtracker.dao.JH;

import com.teamc.moodtracker.dto.Board_LikeDto;
import com.teamc.moodtracker.dto.Comment_LikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JH_CommentLikeDao {
    public boolean isMyLikeComment(Comment_LikeDto dto);
    public int getCmlike_id(Comment_LikeDto dto);
    public List<Integer> getCmlike_idAll(int cm_id);

    public int addCommentLike(Comment_LikeDto dto);

    public void delCommentLike(int cmlike_id);

    public int commentLikeCount(int cm_id);


}
