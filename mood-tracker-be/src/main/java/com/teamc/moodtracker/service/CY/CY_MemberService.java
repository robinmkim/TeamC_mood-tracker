package com.teamc.moodtracker.service.CY;

import com.teamc.moodtracker.dao.CY.CY_MemberDao;
import com.teamc.moodtracker.dto.CY.CY_MemberDto;
import com.teamc.moodtracker.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CY_MemberService {
    @Autowired
    private CY_MemberDao cdao ;

    public CY_MemberDto getMemberInfo_board(int mid){
        return cdao.getMemberInfo_board(mid);
    }

    public void updateProfileImage(MemberDto dto) {
        cdao.updateProfileImage(dto);
    }

    public void updateProfileName(MemberDto dto) {
        cdao.updateProfileName(dto);
    }

    public void updateProfilePassword(MemberDto dto) {
        cdao.updateProfilePassword(dto);
    }

    public void updateProfileHandle(MemberDto dto) {
        cdao.updateProfileHandle(dto);
    }

    public void updateProfileGender(MemberDto dto) {
        cdao.updateProfileGender(dto);
    }

    public void updateProfileBio(MemberDto dto) {
        cdao.updateProfileBio(dto);
    }

    public void checkPassword(MemberDto dto) {
        int res = cdao.checkPassword(dto);
        if (res == 0) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
