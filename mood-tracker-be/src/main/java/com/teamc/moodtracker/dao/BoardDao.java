package com.teamc.moodtracker.dao;


import com.teamc.moodtracker.dto.BoardDetailDto;
import com.teamc.moodtracker.dto.MediaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {
    public void addBoard(BoardDetailDto dto);

    public void addMedia(MediaDto dto);

    public BoardDetailDto getBoardDetail(int b_id);

    public List<Integer> getBoardList(Map<String, Object> params);

    public void updateBoard(BoardDetailDto dto);

    public List<MediaDto> getMediaList(int b_id);

    public void delMedia(int md_id);

    public void delBoard(int b_id);

    public List<BoardDetailDto> getCommentList(int b_id);
}
