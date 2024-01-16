package com.teamc.moodtracker.service.CY;

import com.teamc.moodtracker.dao.CY.CY_BoardDao;
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
//        System.out.println("----------board service impl---------");
//        System.out.println(cdao.getSentiment(mid)+"service impl");

        return cdao.getSentiment(mid);
    }

    public List<Integer> getMyBoardList(int lastRowNum, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("lastRowNum",lastRowNum);
        params.put("mid", mid);
        return cdao.getMyBoardList(params);
    }

    public List<Integer> getLikedBoardList(int lastRowNum, int mid){
        Map<String, Object> params = new HashMap();
        params.put("lastRowNum",lastRowNum);
        params.put("mid", mid);
        return cdao.getLikedBoardList(params);
    }

    public List<Integer> getListByDate(String regdate, int mid) {
        Map<String, Object> params = new HashMap();
        params.put("regdate",regdate);
        params.put("mid", mid);
        return cdao.getListByDate(params);
    }

    public Integer getConsecPostsCnt(int mid){
        return cdao.getConsecPostsCnt(mid);
    }

    public Integer getThisMonthPosts(int mid){
        return cdao.getThisMonthPosts(mid);
    }

    public String getMainSentiment(int mid){
        return cdao.getMainSentiment(mid);
    }
}
