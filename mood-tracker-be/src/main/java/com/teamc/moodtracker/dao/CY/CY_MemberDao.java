package com.teamc.moodtracker.dao.CY;

import com.teamc.moodtracker.dto.CY.CY_MemberDto;
import com.teamc.moodtracker.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CY_MemberDao {
    public CY_MemberDto getMemberInfo_board(int mid);

    public void updateProfileImage(MemberDto dto);

    public void updateProfileName(MemberDto dto);

    public void updateProfileEmail(MemberDto dto);

    public void updateProfilePassword(MemberDto dto);

    public void updateProfileHandle(MemberDto dto);

    public void updateProfileGender(MemberDto dto);

    public void updateProfileBio(MemberDto dto);

    public int checkPassword(MemberDto dto);
}
