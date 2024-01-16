package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.JH_CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JH_CommentDao {
    public int commentCount(int b_id);
    public List<JH_CommentDto> getCommentList(int b_id);

    public List<JH_CommentDto> getCommentListDetail(int b_id) ;

    public int addComment(JH_CommentDto dto);

    public void delComment(int cm_id);
}
