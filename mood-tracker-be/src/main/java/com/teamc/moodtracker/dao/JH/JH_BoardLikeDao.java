package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.Board_LikeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JH_BoardLikeDao {
    public boolean isMyLike(Board_LikeDto dto);
    public int getBlike_id(Board_LikeDto dto);

    public int addBoardLike(Board_LikeDto dto);

    public void delBoardLike(int blike_id);


}
