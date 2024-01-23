package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.BoardLikeDao;
import com.teamc.moodtracker.dto.BoardLikeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BoardLikeService {
    @Autowired
    private BoardLikeDao dao;
    public boolean isMyLike(BoardLikeDto dto) {
        return dao.isMyLike(dto);
    }
    public int getBlike_id(BoardLikeDto dto) {
        return dao.getBlike_id(dto);
    }
    public int addBoardLike(BoardLikeDto dto) {
        return dao.addBoardLike(dto);
    }
    public void delBoardLike(int blike_id) {
        dao.delBoardLike(blike_id);
    }
    public int boardLikeCount(int b_id) {
        return dao.boardLikeCount(b_id);
    }
    @Transactional
    public void delBoardLikeAll(int b_id) {
        List<Integer> likeList = dao.getBlike_idAll(b_id);
        for (int like : likeList){
            dao.delBoardLike(like);

        }
    }
}
