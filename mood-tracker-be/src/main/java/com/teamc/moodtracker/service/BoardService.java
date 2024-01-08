package com.teamc.moodtracker.service;


import com.teamc.moodtracker.dao.BoardDao;
import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.MediaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {
    @Autowired
    private BoardDao dao;

    @Transactional
    public void addBoardContent(BoardDto dto, List<MediaDto> mediaList) {
        dao.addBoard(dto);
        for (MediaDto mediaDto : mediaList) {
            mediaDto.setB_id(dto.getB_id());
            dao.addMedia(mediaDto);
        }
    }

    public BoardDto getBoardDetail(int b_id) {
        return dao.getBoardDetail(b_id);
    }

    public List<Integer> getBoardList(int lastRowNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("lastRowNum", lastRowNum);
        return dao.getBoardList(params);
    }

}
