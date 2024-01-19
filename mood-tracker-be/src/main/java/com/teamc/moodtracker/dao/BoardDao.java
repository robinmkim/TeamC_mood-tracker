package com.teamc.moodtracker.dao;


import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.MediaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardDao {
    public void addBoard(BoardDto dto);

    public void addMedia(MediaDto dto);

    public BoardDto getBoardDetail(int b_id);

    public List<Integer> getBoardList(Map<String, Object> params);
}
