package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.BoardLikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeDao {
    public boolean isMyLike(BoardLikeDto dto);
    public int getBlike_id(BoardLikeDto dto);
    public List<Integer> getBlike_idAll(int b_id);

    public int addBoardLike(BoardLikeDto dto);

    public void delBoardLike(int blike_id);

    public int boardLikeCount(int b_id);
}
