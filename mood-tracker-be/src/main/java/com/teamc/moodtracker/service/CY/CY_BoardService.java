package com.teamc.moodtracker.service.CY;

import com.teamc.moodtracker.dao.CY.CY_BoardDao;
import com.teamc.moodtracker.dto.CY.CY_BoardDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CY_BoardService {
    @Autowired
    private CY_BoardDao cdao;

    public List<Map<String, Object>> getSentiment(int mid) {
        // System.out.println("----------board service impl---------");
        // System.out.println(cdao.getSentiment(mid)+"service impl");

        return cdao.getSentiment(mid);
    }

    public List<Integer> getMyBoardList(int lastRowNum, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("lastRowNum", lastRowNum);
        params.put("mid", mid);
        return cdao.getMyBoardList(params);
    }

    public List<Integer> getLikedBoardList(int lastRowNum, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("lastRowNum", lastRowNum);
        params.put("mid", mid);
        return cdao.getLikedBoardList(params);
    }

    public List<Integer> getListByDate(String regdate, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("regdate", regdate);
        params.put("mid", mid);
        return cdao.getListByDate(params);
    }

    public Integer getConsecPostsCnt(int mid) {
        return cdao.getConsecPostsCnt(mid);
    }

    public Integer getThisMonthPosts(int mid) {
        return cdao.getThisMonthPosts(mid);
    }

    public String getMainSentiment(int mid) {
        return cdao.getMainSentiment(mid);
    }

    public List<CY_BoardDto> SearchBoard(String b_content) {
        Map<String, Object> params = new HashMap<>();
        params.put("b_content", b_content);
        return cdao.SearchBoard(params); // 매개변수 맵을 전달하도록 수정
    }

    // 달력 감정
    public String getTopSentiment(String regdate, int m_id) {
        Map<String, Object> params = new HashMap();
        params.put("regdate", regdate);
        params.put("m_id", m_id);
        return cdao.getTopSentiment(params);
    }
}
