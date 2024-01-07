package com.teamc.moodtracker.dao;


import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.MediaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    public void addBoard(BoardDto dto);

    public void addMedia(MediaDto dto);


}
