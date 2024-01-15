package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.CY_BoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Mapper
public interface CY_BoardDao {
//    public List<Map<String, Object>> getSentiment(int m_id);
    public List<Map<String, Object>> getSentiment(int mid);

    public List<Integer> getMyBoardList(Map<String, Object> params);

    public List<Integer> getLikedBoardList(Map<String, Object> params) ;

    public List<Integer> getListByDate(Map<String, Object> params) ;
}

