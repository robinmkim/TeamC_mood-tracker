package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dto.Board_LikeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JH_BoardLikeService {

    public boolean isMyLike(Board_LikeDto dto);
    public int getBlike_id(Board_LikeDto dto);
    public void delBoardLikeAll(int b_id);

    public int addBoardLike(Board_LikeDto dto);

    public void delBoardLike(int blike_id);

    public int boardLikeCount(int b_id);
}
