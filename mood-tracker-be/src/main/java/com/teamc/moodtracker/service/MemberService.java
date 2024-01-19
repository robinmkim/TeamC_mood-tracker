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

}
