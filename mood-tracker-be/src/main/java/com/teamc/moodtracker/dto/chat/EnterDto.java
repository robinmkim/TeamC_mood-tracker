package com.teamc.moodtracker.dto.chat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterDto {
    private int myMemberId;
    private int otherMemberId;
}
