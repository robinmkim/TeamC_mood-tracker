package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.JH_MemberDao;
import com.teamc.moodtracker.dto.JH_MemberDto;
import org.springframework.stereotype.Service;

@Service
public interface JH_MemberService {
    public JH_MemberDto getMemberInfo_board(int m_id); // board detail user 정보
}
