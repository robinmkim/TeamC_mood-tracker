package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.CY_MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CY_MemberDao {
    public CY_MemberDto getMemberInfo_board(int mid);
}
