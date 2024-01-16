package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dto.Board_LikeDto;
import org.springframework.stereotype.Service;

@Service
public interface JH_BoardLikeService {

    public boolean isMyLike(Board_LikeDto dto);
    public int getBlike_id(Board_LikeDto dto);

    public int addBoardLike(Board_LikeDto dto);

    public void delBoardLike(int blike_id);
}
