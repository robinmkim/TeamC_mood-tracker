package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface MemberDao {
    public int addMember(MemberDto dto);

    public MemberDto getMemberByMEmail(String m_email);

    public int checkEmailExists(String email);

    public MemberDto getMemberInfo_board(int mid); // 윤영호

    public String getMemberName(int m_id);

    // 검색 추가 주우진
    public List<MemberDto> SearchMember(String m_name);

    public int checkHandleExist(String handle);
}
