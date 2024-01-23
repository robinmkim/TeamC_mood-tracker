package com.teamc.moodtracker.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendChat {
    private int roomId;
    private int memberId;
    private int otherMemberId;
    private String message;
    private String status;
}
