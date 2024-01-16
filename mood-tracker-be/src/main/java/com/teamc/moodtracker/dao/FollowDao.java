package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.follow.MyFollow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowDao {

    public List<MemberDto> getMyFollow(int memberId);

    public void newFollow(MyFollow myFollow);

    public void deleteFollow(MyFollow myFollow);
}
