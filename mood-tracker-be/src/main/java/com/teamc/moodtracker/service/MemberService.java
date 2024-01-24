package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.MemberDao;
import com.teamc.moodtracker.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberDao dao;

    public int addMember(MemberDto dto) {
        return dao.addMember(dto);
    }

    public MemberDto getMemberByMEmail(String m_email) {
        return dao.getMemberByMEmail(m_email);
    }

    public MemberDto getMemberInfo_board(int mid) {
        return dao.getMemberInfo_board(mid);
    } // 윤영호

    public List<MemberDto> SearchMember(String m_name) {
        return dao.SearchMember(m_name);
    }

    public int checkHandleExist(String handle) {
        System.out.println("checkHandleExist Service: " +  handle);
        System.out.println("checkHandleExist Service: " +  dao.checkHandleExist(handle));
        return dao.checkHandleExist(handle);
    }

    public void updateProfileImage(MemberDto dto) {
        dao.updateProfileImage(dto);
    }

    public void updateProfileName(MemberDto dto) {
        dao.updateProfileName(dto);
    }

    public void updateProfilePassword(MemberDto dto) {
        dao.updateProfilePassword(dto);
    }

    public void updateProfileHandle(MemberDto dto) {
        dao.updateProfileHandle(dto);
    }

    public void updateProfileGender(MemberDto dto) {
        dao.updateProfileGender(dto);
    }

    public void updateProfileBio(MemberDto dto) {
        dao.updateProfileBio(dto);
    }

    public void checkPassword(MemberDto dto) {
        int res = dao.checkPassword(dto);
        if (res == 0) {
            throw new IllegalArgumentException();
        }
    }
}
