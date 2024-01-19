package com.teamc.moodtracker.service.JH;


import com.teamc.moodtracker.dao.JH.JH_BoardDao;
import com.teamc.moodtracker.dao.JH.JH_CommentDao;
import com.teamc.moodtracker.dto.JH.JH_BoardDto;
import com.teamc.moodtracker.dto.JH.JH_CommentDto;
import com.teamc.moodtracker.dto.MediaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JH_BoardService {
    @Autowired
    private JH_BoardDao dao;

    @Autowired
    private JH_BoardLikeService likeService;


    @Transactional
    public void updateBoardContent(JH_BoardDto dto, List<MediaDto> mediaList) {
        dao.updateBoard(dto);

        // 후에 기존 file은 삭제하는 로직 추가하기
        // 1. dto.getB_id를 이용해서 list 뽑고
        // 2. 삭제

        for (MediaDto mediaDto : mediaList) {
            mediaDto.setB_id(dto.getB_id());
            dao.addMedia(mediaDto);
        }
    }

    public List<MediaDto> getMediaList(int b_id) {
        return dao.getMediaList(b_id);
    };
    public void delMedai(int md_id) {
        dao.delMedai(md_id);
    };

    @Transactional
    public void addBoardContent(JH_BoardDto dto, List<MediaDto> mediaList) {
        dao.addBoard(dto);
        for (MediaDto mediaDto : mediaList) {
            mediaDto.setB_id(dto.getB_id());
            dao.addMedia(mediaDto);
        }
    }

    public JH_BoardDto getBoardDetail(int b_id) {
        return dao.getBoardDetail(b_id);
    }

    public List<Integer> getBoardList(int lastRowNum) {
        Map<String, Object> params = new HashMap<>();
        params.put("lastRowNum", lastRowNum);
        return dao.getBoardList(params);
    }

    @Transactional
    public void delPost (int b_id) {
        System.out.println("delPost");
        if(likeService.boardLikeCount(b_id) > 0){
            likeService.delBoardLikeAll(b_id);
        }
        dao.delBoard(b_id);
    }




}
