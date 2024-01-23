package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentDao {
    public int commentCount(int b_id);

    public List<CommentDto> getCommentList(int b_id);

    public List<CommentDto> getCommentListDetail(int b_id);

    public int addComment(CommentDto dto);

    public void delComment(int cm_id);

    public List<Integer> getCm_idList(int b_id);

    public CommentDto getCommentDetail(int cm_id);
}
