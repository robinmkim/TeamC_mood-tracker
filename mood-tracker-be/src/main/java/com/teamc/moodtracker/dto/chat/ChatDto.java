package com.teamc.moodtracker.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private Long roomId;
    private Long memberId;
    private String message;
    private LocalDateTime sendTime;


    public ChatDto(Long roomId, Long memberId, String message) {
        this.roomId = roomId;
        this.memberId = memberId;
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }
}
