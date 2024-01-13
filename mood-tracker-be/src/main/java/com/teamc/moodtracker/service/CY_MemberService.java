package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.CY_MemberDao;
import com.teamc.moodtracker.dto.CY_MemberDto;
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
}
