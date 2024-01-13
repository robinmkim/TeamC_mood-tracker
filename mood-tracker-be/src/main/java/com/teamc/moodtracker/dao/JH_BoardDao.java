package com.teamc.moodtracker.dao;


import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.JH_BoardDto;
import com.teamc.moodtracker.dto.MediaDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface JH_BoardDao {
    public void addBoard(JH_BoardDto dto);

    public void addMedia(MediaDto dto);

    public JH_BoardDto getBoardDetail(int b_id);

    public List<Integer> getBoardList(Map<String, Object> params);

    public void updateBoard(JH_BoardDto dto);

    public List<MediaDto> getMediaList(int b_id);

    public void delMedai(int md_id);
}
