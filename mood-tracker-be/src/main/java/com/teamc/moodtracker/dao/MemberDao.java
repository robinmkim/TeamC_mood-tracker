package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.CY_MemberDto;
import com.teamc.moodtracker.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public int addMember(MemberDto dto);

    public MemberDto getMemberByMEmail(String m_email);

    public int checkEmailExists(String email);

    public MemberDto getMemberInfo_board(int mid); //윤영호

    public String getMemberName(int m_id);
}
