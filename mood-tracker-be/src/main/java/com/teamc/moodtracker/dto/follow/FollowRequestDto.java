package com.teamc.moodtracker.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequestDto {
    private int followerId;
    private int followedId;
}
