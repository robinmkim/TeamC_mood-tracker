package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.JH_MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JH_MemberDao {
    public JH_MemberDto getMemberInfo_board(int m_id); // board detail user 정보
}
