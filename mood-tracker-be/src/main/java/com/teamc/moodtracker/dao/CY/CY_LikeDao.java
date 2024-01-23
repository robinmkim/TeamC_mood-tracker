package com.teamc.moodtracker.dao.CY;

import com.teamc.moodtracker.dto.CY.CY_LikeDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CY_LikeDao {
    public void addLike(CY_LikeDto cdto);
    public void deleteLike();

    public int likeCheck(int mid, int bid);
    public int cntLike(int bid);

}
