package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.MyPageDao;
import com.teamc.moodtracker.dto.BoardDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MyPageService {
    @Autowired
    private MyPageDao dao;

    public List<Map<String, Object>> getSentiment(int mid) {
        // System.out.println("----------board service impl---------");
        // System.out.println(cdao.getSentiment(mid)+"service impl");

        return dao.getSentiment(mid);
    }

    public List<Integer> getMyBoardList(int lastRowNum, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("lastRowNum", lastRowNum);
        params.put("mid", mid);
        return dao.getMyBoardList(params);
    }

    public List<Integer> getLikedBoardList(int lastRowNum, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("lastRowNum", lastRowNum);
        params.put("mid", mid);
        return dao.getLikedBoardList(params);
    }

    public List<Integer> getListByDate(String regdate, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("regdate", regdate);
        params.put("mid", mid);
        return dao.getListByDate(params);
    }

    public Integer getConsecPostsCnt(int mid) {
        return dao.getConsecPostsCnt(mid);
    }

    public Integer getThisMonthPosts(int mid) {
        return dao.getThisMonthPosts(mid);
    }

    public String getMainSentiment(int mid) {
        return dao.getMainSentiment(mid);
    }

    public List<BoardDto> SearchBoard(String b_content) {
        Map<String, Object> params = new HashMap<>();
        params.put("b_content", b_content);
        return dao.SearchBoard(params); // 매개변수 맵을 전달하도록 수정
    }

    // 달력 감정
    public String getTopSentiment(String regdate, int m_id) {
        Map<String, Object> params = new HashMap();
        params.put("regdate", regdate);
        params.put("m_id", m_id);
        return dao.getTopSentiment(params);
    }
}
