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

    public void updateProfileImage(MemberDto dto);

    public void updateProfileName(MemberDto dto);

    public void updateProfileEmail(MemberDto dto);

    public void updateProfilePassword(MemberDto dto);

    public void updateProfileHandle(MemberDto dto);

    public void updateProfileGender(MemberDto dto);

    public void updateProfileBio(MemberDto dto);

    public int checkPassword(MemberDto dto);
}
