package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.JH_CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JH_CommentDao {
    public int commentCount(int cm_bid);
    public List<JH_CommentDto> getCommentList(int cm_bid);

    public List<JH_CommentDto> getCommentListDetail(int cm_bid) ;
}
