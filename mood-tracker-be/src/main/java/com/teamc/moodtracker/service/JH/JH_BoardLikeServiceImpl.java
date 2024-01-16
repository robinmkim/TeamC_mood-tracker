package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dao.JH.JH_BoardLikeDao;
import com.teamc.moodtracker.dto.Board_LikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JH_BoardLikeServiceImpl implements JH_BoardLikeService{
    @Autowired
    private JH_BoardLikeDao dao;


    @Override
    public boolean isMyLike(Board_LikeDto dto) {
        return dao.isMyLike(dto);
    }

    @Override
    public int getBlike_id(Board_LikeDto dto) {
        return dao.getBlike_id(dto);
    }

    @Override
    public int addBoardLike(Board_LikeDto dto) {
        return dao.addBoardLike(dto);
    }

    @Override
    public void delBoardLike(int blike_id) {
        dao.delBoardLike(blike_id);
    }
}
