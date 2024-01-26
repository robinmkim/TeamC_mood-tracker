package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.follow.FollowCount;
import com.teamc.moodtracker.dto.follow.FollowRequestDto;
import com.teamc.moodtracker.dto.follow.MyFollow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowDao {

    public List<MemberDto> getMyFollow(int memberId);

    public void makeFollow(FollowRequestDto followRequestDto);

    public void deleteFollow(FollowRequestDto followRequestDto);

    public FollowCount getFollowCnt(int memberId);

    public int checkFollow(FollowRequestDto followRequestDTO);

    public List<MemberDto> FollowerId(int followerId);

    public List<MemberDto> FolloweredId(int followeredId);
}
