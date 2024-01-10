package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.MemberDao;
import com.teamc.moodtracker.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
