package com.teamc.moodtracker.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Getter
@Builder
@AllArgsConstructor
public class FollowCount {
    private int followerCnt;
    private int followedCnt;
}
