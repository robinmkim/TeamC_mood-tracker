package com.teamc.moodtracker.dto.follow;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyFollow {
    private int followedId;
    private int followerId;
    private LocalDateTime followDate;

    public MyFollow(int followedId, int followerId) {
        this.followedId = followedId;
        this.followerId = followerId;
    }
}
