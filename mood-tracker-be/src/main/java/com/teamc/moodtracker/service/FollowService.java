package com.teamc.moodtracker.service;

import com.teamc.moodtracker.dao.FollowDao;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.follow.MyFollow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowDao followDao;

    public List<MemberDto> getMyFollow(int memberId) {
        return followDao.getMyFollow(memberId);
    }

    public void newFollow(MyFollow myFollow) {
        followDao.newFollow(myFollow);
    }

    public void deleteFollow(MyFollow myFollow) {
        followDao.deleteFollow(myFollow);
    }
}
