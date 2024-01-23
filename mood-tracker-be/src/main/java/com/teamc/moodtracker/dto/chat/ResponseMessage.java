package com.teamc.moodtracker.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private int roomId;
    private int memberId;
    private String memberName;
    private String message;
    private LocalDateTime sendTime;
    private String status;
}
