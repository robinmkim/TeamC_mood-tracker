package com.teamc.moodtracker.dao.CY;

import com.teamc.moodtracker.dto.CY.CY_MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CY_MemberDao {
    public CY_MemberDto getMemberInfo_board(int mid);
}
