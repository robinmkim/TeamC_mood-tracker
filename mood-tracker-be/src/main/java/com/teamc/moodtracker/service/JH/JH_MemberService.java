package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dto.JH.JH_MemberDto;
import org.springframework.stereotype.Service;

@Service
public interface JH_MemberService {
    public JH_MemberDto getMemberInfo_board(int m_id); // board detail user 정보
}
