package com.teamc.moodtracker.dao.JH;

import com.teamc.moodtracker.dto.Board_LikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JH_BoardLikeDao {
    public boolean isMyLike(Board_LikeDto dto);
    public int getBlike_id(Board_LikeDto dto);
    public List<Integer> getBlike_idAll(int b_id);

    public int addBoardLike(Board_LikeDto dto);

    public void delBoardLike(int blike_id);

    public int boardLikeCount(int b_id);

}
