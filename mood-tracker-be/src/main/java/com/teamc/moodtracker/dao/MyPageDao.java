package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyPageDao {
    public List<Map<String, Object>> getSentiment(int mid);

    public List<Integer> getMyBoardList(Map<String, Object> params);

    public List<Integer> getLikedBoardList(Map<String, Object> params);

    public List<Integer> getListByDate(Map<String, Object> params);

    public Integer getConsecPostsCnt(int mid);

    public Integer getThisMonthPosts(int mid);

    public String getMainSentiment(int mid);

    // 개시물 검색을 위해서 추가
    public List<BoardDto> SearchBoard(Map<String, Object> params);

    // 달력 감정 데이터
    public String getTopSentiment(Map<String, Object> params);
}