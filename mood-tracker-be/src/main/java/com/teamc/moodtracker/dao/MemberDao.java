package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public int addMember(MemberDto dto);
    public MemberDto getMemberByMEmail(String m_email);
}
