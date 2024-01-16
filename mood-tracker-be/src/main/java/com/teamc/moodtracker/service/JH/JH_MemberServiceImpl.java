package com.teamc.moodtracker.service.JH;

import com.teamc.moodtracker.dao.JH.JH_MemberDao;
import com.teamc.moodtracker.dto.JH.JH_MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JH_MemberServiceImpl implements JH_MemberService{
    @Autowired
    private JH_MemberDao memberDao;


    @Override
    public JH_MemberDto getMemberInfo_board(int m_id) {
        return memberDao.getMemberInfo_board(m_id);
    }
}
